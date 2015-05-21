/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package warehouseejb;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author tiago
 */
@WebService(serviceName = "WarehouseService")
@Stateless()
public class WarehouseService {
    
    @WebMethod(operationName = "getAllOrders")
    public List<BookOrder> getAllOrders() {
        try {
            return BookOrder.findAll();
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @WebMethod(operationName = "getOrder")
    public BookOrder getOrder(@WebParam(name = "id") int id) {
        try {
            return BookOrder.findById(id);
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @WebMethod(operationName = "saveOrder")
    public void saveOrder(@WebParam(name = "order") BookOrder order) {
        try {
            //TODO write your implementation code here:
            order.save();
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}