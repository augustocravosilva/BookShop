/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationejb;

import applicationejbAPI.StoreBeanRemote;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import logic.Client;
import logic.Book;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import logic.BookOrder;
import logic.BookSell;
import simple.SimpleBook;
import simple.SimpleClient;
import simple.SimpleOrder;

/**
 *
 * @author augusto
 * 
 * To persist data:
 * create a DB
 * Add a pool to glassfish -> input user, password, url of DB
 * Add a Resource -> Just choose the new pool
 * Edit persistence.xml -> change the source to the new resource
 * 
 * 
 */
@Stateless
public class StoreBean implements StoreBeanRemote {
    @Resource(mappedName = "jms/EAppQueue")
    private Queue eAppQueue;
    @Inject
    @JMSConnectionFactory("jms/EAppQueueFactory")
    private JMSContext context;

    @PersistenceContext(unitName = "Application-ejbPU")
    private EntityManager em;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public int orderBook(String isbn, int quantity, int clientId) {
        BookOrder bo = new BookOrder();
        Client cli = em.getReference(Client.class, clientId);
        bo.setClientid(cli);
        Book b = em.getReference(Book.class, isbn);
        bo.setIsbn(b);
        bo.setQuantity(quantity);
        if(quantity > b.getStock())
        {
            bo.setState(WAITING_EXPEDITION);
            persist(bo);
            em.flush();
            sendJMSMessageToEAppQueue(isbn+":"+10*quantity); //warehouse use getBook(isbn) to get more details
        } else{
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, 1);
            bo.setState(DISPATCHED_AT + sdf.format(c.getTime()));
            persist(bo);
            em.flush();
            SimpleBook sb = getSimpleBook(b);
            sendEmail(cli,"Order dispatched",String.format("Hello,\nYour order is ready.\n\nDetails:\nBook: %s\n"
                    + "Quantity: %d\nTotal: %.2f\nState: %s\n\nThank you",sb.title,bo.getQuantity(),bo.getQuantity()*sb.price,bo.getState()));
        }
        return bo.getId();
    }
    
    @Override
    public boolean buyBook(String isbn, int quantity, int clientId, double total) {
        Book b = em.getReference(Book.class, isbn);
        if(b.getStock() < quantity){
            return false;
        }
        
        BookSell bs = new BookSell();
        Client c = em.getReference(Client.class, clientId);
        bs.setClientid(c);
        bs.setIsbn(b);
        bs.setQuantity(quantity);
        bs.setTotalprice(total);
        persist(bs);
        b.setStock(b.getStock()-quantity);
        persist(b);
        em.flush();
        return true;
    }
    
    @Override
    public int newClient(String name, String adress, String email)
    {
        Client c = new Client();
        c.setFullname(name);
        c.setAdress(adress);
        c.setEmail(email);
        persist(c);
        em.flush();
        return c.getId();
    }
    
    
    
    private JsonObject callGoogleAPI(String isbn)
    {
        String sURL = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn; 
        System.out.println(sURL);
        URL url;
            try {
                url = new URL(sURL);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.connect();
                    // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //convert the input stream to a json element
        JsonObject rootobj = root.getAsJsonObject(); //may be an array, may be an object. 
        System.out.println(rootobj.toString());
        return rootobj;
            } catch (MalformedURLException ex) {
                //Logger.getLogger(StoreBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                //Logger.getLogger(StoreBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }

    private void persist(Object object) {
        em.persist(object);
    }   

    private void sendJMSMessageToEAppQueue(String messageData) {
        context.createProducer().send(eAppQueue, messageData);
    }
    
    @Override
    public List<SimpleBook> getBooks() {
        List<SimpleBook> list = new ArrayList();
        List<Book> resultList = em.createNamedQuery("Book.findAll").getResultList();
        for(Book b : resultList)
        {
            SimpleBook book = getSimpleBook(b);
            list.add(book);
        }
        return list;
    }

    private SimpleBook getSimpleBook(Book b) {
        JsonObject o = callGoogleAPI(b.getIsbn());
        SimpleBook book = new SimpleBook();
        book.isbn = b.getIsbn();
        book.stock = b.getStock();
        if(o!=null)
        {
            JsonObject googlebook = o.get("items").getAsJsonArray().get(0).getAsJsonObject();
            book.image = googlebook.get("volumeInfo").getAsJsonObject().get("imageLinks").getAsJsonObject().get("thumbnail").getAsString();
            book.title = googlebook.get("volumeInfo").getAsJsonObject().get("title").getAsString();
            book.price = 0.0;
            try{
                book.price = googlebook.get("saleInfo").getAsJsonObject().get("listPrice").getAsJsonObject().get("amount").getAsDouble();
            } catch(Exception e)
            {
            }
        }
        return book;
    }

    @Override
    public SimpleBook getBook(String isbn) {
        Book b = em.getReference(Book.class, isbn);
        return getSimpleBook(b);
    }

    @Override
    public String getGoogleBookAsString(String isbn) {
        return callGoogleAPI(isbn).toString();
    }

    @Override
    public void receiveStock(String isbn, int quantity) {
        Book b = em.getReference(Book.class,isbn);
        SimpleBook sb = getSimpleBook(b);
        int left_quantity = quantity + b.getStock(); //update all orders than can be satisfied with new stock
        List<BookOrder> bos = b.getBookOrderCollection();
        for(BookOrder bo : bos)
        {
            if(!bo.getState().startsWith(DISPATCHED_AT) && bo.getQuantity() <= left_quantity)
            {
                //order is ready
                bo.setState(DISPATCHED_AT + sdf.format(Calendar.getInstance().getTime()));
                left_quantity -= bo.getQuantity();
                System.out.println("--> Quanity is now " + left_quantity);
                persist(bo);
                Client cli = bo.getClientid();
                sendEmail(cli,"Order dispatched",String.format("Hello,\nYour order is ready.\n\nDetails:\nBook: %s\n"
                    + "Quantity: %d\nTotal: %.2f\nState: %s\n\nThank you",sb.title,bo.getQuantity(),bo.getQuantity()*sb.price,bo.getState()));
            }
        }//the ones left are stored in the shop
        b.setStock(left_quantity);
        persist(b);
        em.flush();
    }
    private static final String DISPATCHED_AT = "dispatched at ";
    private static final String DISPATCH_WILL_OCCUR_AT = "dispatch will occur at ";
    private static final String WAITING_EXPEDITION = "waiting expedition";

    @Override
    public void notifyOrderAboutToShip(String isbn, int quantity) { //notifies the one that can be satisfied only with new stock
        Book b = em.getReference(Book.class,isbn);
        int left_quantity = quantity;
        List<BookOrder> bos = b.getBookOrderCollection();
        for(BookOrder bo : bos)
        {
            if(bo.getState().startsWith(WAITING_EXPEDITION) && bo.getQuantity() <= left_quantity)
            {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, 2);
                bo.setState(DISPATCH_WILL_OCCUR_AT + sdf.format(c.getTime()));
                left_quantity -= bo.getQuantity();
                persist(bo);
            }
        }
        em.flush();
    }
   
    /*
    * To use email you must be connect to feup network
    */
    
    private boolean sendEmail(Client to, String subject, String body)
    {
        final int port = 465;
        final String host = "smtp.fe.up.pt";
        final String from = "bookshop@fe.up.pt";
        final String from_name = "BookShop";
        
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.auth", false);
        
        Session session = Session.getInstance(props);
        session.setDebug(true);
        
        MimeMessage message = new MimeMessage(session);
        
        try {
            message.setFrom(new InternetAddress(from,from_name));
            InternetAddress[] address = {new InternetAddress(to.getEmail(),to.getFullname())};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(body);
            Transport.send(message);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    @Override
    public SimpleClient getClient(int clientId) {
        Client c = em.find(Client.class, clientId);
        SimpleClient sc = new SimpleClient();
        sc.address = c.getAdress();
        sc.name = c.getFullname();
        sc.email = c.getEmail();
        sc.id = c.getId();
        return sc;
    }

    @Override
    public int validateClient(String email, String password) {
        try{
            Client c = em.createNamedQuery("Client.findByEmail", Client.class).setParameter("email", email).getSingleResult();
            if(password.equals("TDIN"))
                return c.getId();
            else return -1;
        }catch(NoResultException e){
            return -1;
        }
    }
    
    @Override
    public List<Client> getClients() {
        return em.createNamedQuery("Client.findAll").getResultList();
    }

    @Override
    public void editClient(int clientId, String name, String adress, String email) {
        Client c = em.find(Client.class, clientId);
        c.setFullname(name);
        c.setAdress(adress);
        c.setEmail(email);
        persist(c);
        em.flush();
    }

    @Override
    public SimpleOrder getBookOrder(int orderid) {
        BookOrder bo = em.find(BookOrder.class, orderid);
        SimpleOrder so = new SimpleOrder();
        so.customer = bo.getClientid().getId();
        so.date = sdf.format(bo.getOrderdate());
        so.product_id = bo.getIsbn().getIsbn();
        so.quantity = bo.getQuantity();
        so.state = bo.getState();
        SimpleBook sb = getBook(so.product_id);
        so.unit_price = sb.price;
        so.total = sb.price * so.quantity;
        return so;
    }

    @Override
    public List<SimpleOrder> getBookOrders(int clientId) {
        Client c = em.find(Client.class, clientId);
        List<SimpleOrder> l = new ArrayList();
        for(BookOrder bo : c.getBookOrderCollection())
        {
            l.add(getBookOrder(bo.getId()));
        }
        return l;
    }

    @Override
    public List<BookOrder> getAllOrders() {
        return em.createNamedQuery("BookOrder.findAll", BookOrder.class).getResultList();
    }

    @Override
    public List<BookSell> getAllSells() {
        return em.createNamedQuery("BookSell.findAll", BookSell.class).getResultList();
    }
    
    
    
    
}
