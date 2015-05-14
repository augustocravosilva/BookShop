package warehouseejb;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import sun.util.calendar.CalendarDate;


@MessageDriven(mappedName = "jms/EAppQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class WarehouseBean implements MessageListener {
    /*
    @PersistenceUnit(unitName = "warehouse-ejbPU") //inject from your application server
    EntityManagerFactory emf;
    @Resource //inject from your application server
    UserTransaction utx;*/
    
    public WarehouseBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String m = ((TextMessage)message).getText();
                System.out.println(m);
                /*BookOrder order;
                order = new BookOrder(1, "efgh", "slb", 22, "1993-09-23 03:23:34", null, "maior");
                order.create();*/
                
                BookOrder order = BookOrder.findById(1);
                order.setBookName("benfica");
                order.save();
                
                System.out.println("book - > " + order);
                
            }
            catch (Exception ex) {
                System.err.println("Exception:\n" + ex);
            }
        }
    }
}
