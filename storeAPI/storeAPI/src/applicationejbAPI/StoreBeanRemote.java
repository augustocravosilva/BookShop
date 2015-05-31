/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationejbAPI;

import logic.Client;
import java.util.List;
import javax.ejb.Remote;
import simple.SimpleBook;

/**
 *
 * @author augusto
 */
@Remote
public interface StoreBeanRemote {

    void orderBook(String isbn, int quantity, int clientId);

    String businessMethod();

    void newClient(String name, String adress, String email);

    List<Client> getClients();

    String test();

    List<SimpleBook> getBooks();

    SimpleBook getBook(String isbn);

    String getGoogleBookAsString(String isbn);

    boolean buyBook(String isbn, int quantity, int clientId, double total);

    void receiveStock(String isbn, int quantity);

    void notifyOrderAboutToShip(String isbn, int quantity);
    
    
}
