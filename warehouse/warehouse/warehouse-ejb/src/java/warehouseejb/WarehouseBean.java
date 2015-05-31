package warehouseejb;

import applicationejbAPI.StoreBeanRemote;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import simple.SimpleBook;

//TODO: Then rename the queue for now using this one to test with teacher's example
@MessageDriven(mappedName = "jms/EAppQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class WarehouseBean implements MessageListener {
    private StoreBeanRemote storeBean = lookupStoreBeanRemote();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
                System.out.println("Order received: " + m);
                
                Calendar c = Calendar.getInstance();
                
                String orderParams[] = m.split(":");
                
                if (orderParams.length != 2) {
                    System.err.println("Unexpected params");
                    return;
                }
                
                String isbn = orderParams[0];
                int quantity;
                
                try {
                    quantity = Integer.parseInt(orderParams[1]);
                }
                catch(Exception e) {
                    System.err.println("Quantity not a number!");
                    return;
                }
                
                SimpleBook book = storeBean.getBook(isbn);
                BookOrder order = new BookOrder(isbn, book.title, quantity, sdf.format(c.getTime()));
                order.create();
                
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
