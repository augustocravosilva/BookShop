/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package api;

import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author tiago
 */
@Path("orders")
public class Order {
    
    @Context
    private UriInfo context;
    
    /**
     * Creates a new instance of Order
     */
    public Order() {
    }
    
    
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        return "[\"nabo\"]";
    }
    
    
    /**
     * Returns an order
     * @param id id of the order
     * @return an Order
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getOrder(@PathParam("id") String id) {
        //TODO GET the order with that id
        System.out.println("Returning order w/ id: " + id);
        
        return "{\"id\":\"520\",\"customer\":\"C001\",\"date\":\"2014-11-28T00:00:00\",\"total\":35.50,\"state\":\"P\",\"lines\":[{\"product_id\":\"A001\",\"unit_price\":79.95,\"quatity\":10.0,\"total\":983.39,\"color\":\"BLACK\",\"size\":\"15\"}],\"delivery_address\":\"Rua das Flores N 123\",\"delivery_city\":\"Porto\",\"delivery_zip\":\"4100-000\"}";
    }
    
    /**
     * Returns all client orders
     * @param id client
     * @return all Orders
     */
    @GET
    @Path("client/{id}")
    @Produces("application/json")
    public String getClientOrders(@PathParam("id") String id) {
        //TODO GET the orders from this client
        System.out.println("Returning orders from client w/ id: " + id);

        return "{\"orders\":[{\"id\":1,\"date\":\"2014-11-28T00:00:00\",\"total\":799.5,\"state\":\"Pending\"}]}";
    }
    
    
    /**
     * Register a user
     *
     * @param user details
     * @return user id
     */
    @POST
    @Produces("application/json")
    @Consumes({"application/json"})
    public String register(JsonObject json) {
        System.out.println("making order -> " + json.toString());
        //{"customer":"C001","lines":[{"product_id":"A002.BLACK.13","quantity":1}]}

        if (!json.containsKey("customer") || !json.containsKey("lines")) {
            return "{\"error\": \"wrong parameters\"}";
        }
        
        
        //for each line on order create a separate order
        //return orders ID
        return "[1,2,3]";
    }
}
