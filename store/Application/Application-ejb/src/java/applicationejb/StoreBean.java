/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationejb;

import applicationejbAPI.StoreBeanRemote;
import logic.Client;
import logic.Book;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    //REST test
    @Override
    public String businessMethod() {
       // Book b = new logic.Book("a11113");
       // persist(b);
        sendJMSMessageToEAppQueue("lalala");
        return "hello!";
    }

    private void persist(Object object) {
        em.persist(object);
    }   

    private void sendJMSMessageToEAppQueue(String messageData) {
        context.createProducer().send(eAppQueue, messageData);
    }
    
}
