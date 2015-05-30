/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple;

import java.io.Serializable;

/**
 *
 * @author augusto
 */
public class SimpleBook implements Serializable{
    public String isbn;
    public String title;
    public String image;
    public double price;
    public int stock;

    public SimpleBook() {
        stock = 0;
        price = 0.0;
    }
    
    
}
