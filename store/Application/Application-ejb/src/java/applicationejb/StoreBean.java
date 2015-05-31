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
import static com.google.gson.internal.bind.TypeAdapters.URL;
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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import logic.BookOrder;
import logic.BookSell;
import simple.SimpleBook;

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
    public void orderBook(String isbn, int quantity, int clientId) {
        BookOrder bo = new BookOrder();
        bo.setClientid(em.getReference(Client.class, clientId));
        Book b = em.getReference(Book.class, isbn);
        bo.setIsbn(b);
        bo.setQuantity(quantity);
        if(quantity > b.getStock())
        {
            bo.setState(WAITING_EXPEDITION);
            sendJMSMessageToEAppQueue(isbn+":"+10*quantity); //warehouse use getBook(isbn) to get more details
        } else{
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, 1);
            bo.setState("dispatched at " + sdf.format(c.getTime()));
        }
        persist(bo);
        em.flush();
    }
    
    @Override
    public boolean buyBook(String isbn, int quantity, int clientId, double total) {
        Book b = em.getReference(Book.class, isbn);
        if(b.getStock() < quantity){
            return false;
        }
        
        BookSell bs = new BookSell();
        bs.setClientid(em.getReference(Client.class, clientId));
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
    public void newClient(String name, String adress, String email)
    {
        Client c = new Client();
        c.setFullname(name);
        c.setAdress(adress);
        c.setEmail(email);
        persist(c);
        em.flush();
    }
    
    
    @Override
    public List<Client> getClients() {
        return em.createNamedQuery("Client.findAll").getResultList();
    }
    
    
    //nova ordem de compra

    //public void orderBook(String isbn, int quantity, int clientId) {
    //}

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    //livros - titulos, preco, existencias loja e armazem
    
    
    
    //venda
    
    //receipt
    
    //send email
    
    //request warehouse for 10x quantity
    
    //orderbeingshipped (Warehouse->Store)
    
    //update store stock

    
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
    
    //REST test
    @Override
    public String businessMethod() {

        return "";

       // sendJMSMessageToEAppQueue("lalala");
       
    }

    private void persist(Object object) {
        em.persist(object);
    }   

    private void sendJMSMessageToEAppQueue(String messageData) {
        context.createProducer().send(eAppQueue, messageData);
    }

    @Override
    public String test() {
        return ((Client)(em.createNamedQuery("Client.findAll").getResultList().get(0))).getFullname();
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
        int left_quantity = quantity + b.getStock(); //update all orders than can be satisfied with new stock
        List<BookOrder> bos = b.getBookOrderCollection();
        for(BookOrder bo : bos)
        {
            if(!bo.getState().startsWith(DISPATCHED_AT) && bo.getQuantity() <= left_quantity)
            {
                //order is ready
                //TODO send email
                bo.setState(DISPATCHED_AT + sdf.format(Calendar.getInstance().getTime()));
                left_quantity -= bo.getQuantity();
                System.out.println("--> Quanity is now " + left_quantity);
                persist(bo);
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
    
}
