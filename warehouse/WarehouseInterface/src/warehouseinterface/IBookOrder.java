/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package warehouseinterface;

import java.io.Serializable;

/**
 *
 * @author tiago
 */
public interface IBookOrder extends Serializable  {
    
    public Integer getId();
    
    public void setId(Integer id);
    
    public String getIsbn();
    
    public void setIsbn(String isbn);
    
    public String getBookName();
    
    public void setBookName(String bookName);
    
    public int getQuantity();
    
    public void setQuantity(int quantity);
    
    public String getStatus();
    
    public void setStatus(String status);
    
    public String getOrderDate();
    
    public void setOrderDate(String orderDate);
    
    public String getDispatchDate();
    
    public void setDispatchDate(String dispatchDate);
    
}
