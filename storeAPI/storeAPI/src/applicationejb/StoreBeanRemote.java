/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationejb;

import javax.ejb.Remote;

/**
 *
 * @author augusto
 */
@Remote
public interface StoreBeanRemote {

    void orderBook(String isbn, int quatity, int clientId);

    String businessMethod();
    
}
