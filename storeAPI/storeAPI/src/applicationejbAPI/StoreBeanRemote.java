/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationejbAPI;

import logic.Client;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author augusto
 */
@Remote
public interface StoreBeanRemote {

    void orderBook(String isbn, int quatity, int clientId);

    String businessMethod();

    void newClient(String name, String adress, String email);

    List<Client> getClients();

    String test();
    
    
}
