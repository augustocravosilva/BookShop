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
import java.util.ArrayList;
import logic.Client;
import logic.Book;
import java.util.Collection;
import java.util.List;
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
    

    @Override
    public void orderBook(String isbn, int quatity, int clientId) {
        System.out.println("It was ordered.");
        
    }
    
    @Override
    public void newClient(String name, String adress, String email)
    {
        Client c = new Client();
        c.setFullname(name);
        c.setAdress(adress);
        c.setEmail(email);
        persist(c);
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
    
    
    
}
