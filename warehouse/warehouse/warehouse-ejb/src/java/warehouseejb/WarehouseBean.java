package warehouseejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


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
                BookOrder order;
                order = new BookOrder(1, "qwerty", "pimpampum", 22, "2014-03-23 00:00:00", null, "dsfs");
                order.create();
                
                /*BookOrder order = (BookOrder) BookOrder.findById(1);
                order.setBookName("benficaCampeao");
                order.save();*/
                
                System.out.println("book - > " + order);
                
            }
            catch (Exception ex) {
                System.err.println("Exception:\n" + ex);
            }
        }
    }
}
