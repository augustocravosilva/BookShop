/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package warehouseac;

import java.util.List;
import javax.xml.ws.WebServiceRef;
import warehouseejb.BookOrder;
import warehouseejb.WarehouseService_Service;


//TODO: Delete this - only for testing purpose
public class Main {
    @WebServiceRef(wsdlLocation = "META-INF/wsdl/localhost_8080/WarehouseService/WarehouseService.wsdl")
    private static WarehouseService_Service service;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("HEY");
        List<BookOrder> orders = getAllOrders();
        for(BookOrder o: orders) {
            System.out.println("id: " + o);
            System.out.println("name: " + o.getBookName());
        }
        BookOrder o;
        o = orders.get(0);
        o.setStatus("e");
        System.out.println("saving order...");
        saveOrder(o);
        
        System.out.println("new order:");
        BookOrder o2 = getOrder(o.getId());
        System.out.println("new order status: " + o2.getStatus());
        
        
        //System.out.println(hello("nabo"));
    }
    
    private static List<BookOrder> getAllOrders() {
        warehouseejb.WarehouseService port = service.getWarehouseServicePort();
        return port.getAllOrders();
    }
    
    private static void saveOrder(warehouseejb.BookOrder order) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        warehouseejb.WarehouseService port = service.getWarehouseServicePort();
        port.saveOrder(order);
    }
    
    private static BookOrder getOrder(int id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        warehouseejb.WarehouseService port = service.getWarehouseServicePort();
        return port.getOrder(id);
    }
    
    
    
    
}