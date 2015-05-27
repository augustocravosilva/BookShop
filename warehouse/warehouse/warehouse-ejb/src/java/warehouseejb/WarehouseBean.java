package warehouseejb;

import applicationejbAPI.StoreBeanRemote;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import logic.Client;

//TODO: Then rename the queue for now using this one to test with teacher's example
@MessageDriven(mappedName = "jms/EAppQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class WarehouseBean implements MessageListener {
    private StoreBeanRemote storeBean = lookupStoreBeanRemote();
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
                order = new BookOrder(1, "poiuy", "zenabo", 22, "2014-06-15 00:00:00", null, "dsfs");
                order.create();
                System.out.println("->" + storeBean.test());
                
                System.out.println("-->" + storeBean.getClients().get(0));
                

                
                /*BookOrder order = (BookOrder) BookOrder.findById(1);
                order.setBookName("benficaCampeao");
                order.save();*/
                
                System.out.println("book - > " + order);
                
            }
            catch (JMSException | SQLException ex) {
                System.err.println("Exception:\n" + ex);
            }
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
