/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package warehouseejb;

import applicationejbAPI.StoreBeanRemote;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import warehouseinterface.IBookOrder;
import warehouseinterface.IWarehouseService;


@Stateless()
public class WarehouseService implements IWarehouseService {
    private StoreBeanRemote storeBean = lookupStoreBeanRemote();
    
    @Override
    public List<IBookOrder> getAllOrders() {
        System.out.println("->getAllOrders");
        try {
            return BookOrder.findAll();
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public IBookOrder getOrder(int id) {
        System.out.println("->getOrder");
        try {
            return BookOrder.findById(id);
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;    }
    
    @Override
    public void saveOrder(IBookOrder order) {
        System.out.println("->saveOrder");
        
        try {
            //TODO write your implementation code here:
            BookOrder o = (BookOrder) order;
            o.save();
            if ("closed".equals(order.getStatus())) {
                storeBean.notifyOrderAboutToShip(o.getIsbn(), o.getQuantity());
                System.out.println("->notifying store");
            }
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private StoreBeanRemote lookupStoreBeanRemote() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StoreBeanRemote) c.lookup("java:global/Application/Application-ejb/StoreBean!applicationejbAPI.StoreBeanRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}