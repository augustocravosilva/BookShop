/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationejbAPI;

import logic.Client;
import java.util.List;
import javax.ejb.Remote;
import logic.BookOrder;
import logic.BookSell;
import simple.SimpleBook;
import simple.SimpleClient;
import simple.SimpleOrder;

/**
 *
 * @author augusto
 */
@Remote
public interface StoreBeanRemote {

    int orderBook(String isbn, int quantity, int clientId);

    int newClient(String name, String adress, String email);

    List<SimpleBook> getBooks();

    SimpleBook getBook(String isbn);

    String getGoogleBookAsString(String isbn);

    boolean buyBook(String isbn, int quantity, int clientId, double total);

    void receiveStock(String isbn, int quantity);

    void notifyOrderAboutToShip(String isbn, int quantity);

    SimpleClient getClient(int clientId);

    int validateClient(String email, String password);
    
    List<Client> getClients();

    void editClient(int clientId, String name, String adress, String email);

    SimpleOrder getBookOrder(int orderid);

    List<SimpleOrder> getBookOrders(int clientId);

    List<BookOrder> getAllOrders();

    List<BookSell> getAllSells();

    void changeClientPassword(String password, int clientId);

    boolean addBook(String isbn);
}
