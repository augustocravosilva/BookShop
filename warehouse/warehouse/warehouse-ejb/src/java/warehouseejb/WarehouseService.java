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
import javax.ejb.Stateless;
import warehouseinterface.IBookOrder;
import warehouseinterface.IWarehouseService;


@Stateless()
public class WarehouseService implements IWarehouseService {

    
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
            if (order.getStatus() == "closed") {
                //inform store
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}