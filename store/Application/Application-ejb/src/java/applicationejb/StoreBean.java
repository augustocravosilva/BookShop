/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 *
 * @author augusto
 */
@Stateless
public class StoreBean implements StoreBeanRemote {
    @Resource(mappedName = "jms/EAppQueue")
    private Queue eAppQueue;
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    @Override
    public void orderBook(String isbn, int quatity, int clientId) {
        System.out.println("It was ordered.");
        
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

    @Override
    public String businessMethod() {
        return "hello!";
    }

    private void sendJMSMessageToEAppQueue(String messageData) {
        context.createProducer().send(eAppQueue, messageData);
    }
    
    
}
