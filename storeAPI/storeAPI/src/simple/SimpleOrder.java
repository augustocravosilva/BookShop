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
public class SimpleOrder implements Serializable{
    //public int id;
    public int customer;
    public String date;
    public double total;
    public String state;
    public String product_id;
    public double unit_price;
    public int quantity;
    public int id;
}
