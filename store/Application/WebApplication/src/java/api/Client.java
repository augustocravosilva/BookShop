/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package api;

import applicationejbAPI.StoreBeanRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import simple.SimpleClient;

/**
 * REST Web Service
 *
 * @author tiago
 */
@Path("client")
public class Client {
    StoreBeanRemote storeBean = lookupStoreBeanRemote();
    
    @Context
    private UriInfo context;
    
    /**
     * Creates a new instance of Client
     */
    public Client() {
    }
    
    /**
     * Retrieves representation of an instance of api.Client
     * @return an instance of logic.Client
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        return "[\"nabo\"]";
    }
    
    /**
     * PUT method for updating or creating an instance of Client
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public SimpleClient putJson(@PathParam("id") String id, JsonObject json) {
        System.out.println("id-> " + id);
        System.out.println("json-> " + json.toString());
        /** 
         * json brings only fields to change so it could contain:
         * - name
         * - tax_id
         * - email
         * - street
         * - city
         * - zip_code1
         * - zip_code1
         * - password & password2
        */
        
        if(id == null) // If something went wrong
            throw new WebApplicationException("{\"error\": \"invalid request\"}");
        
        SimpleClient cs = storeBean.getClient(Integer.parseInt(id));
        
        if(json.containsKey("name"))
            cs.name = json.getString("name");
        if(json.containsKey("city"))
            cs.address = json.getString("city");
        if(json.containsKey("email"))
            cs.email = json.getString("email");
        
        storeBean.editClient(Integer.parseInt(id), cs.name, cs.address, cs.email);
        
        if(json.containsKey("password") && json.containsKey("password2"))
            if(json.getString("password").equals(json.getString("password2")))
                storeBean.changeClientPassword(json.getString("password"), Integer.parseInt(id));
        
        return cs;
    }
    
    @POST
    @Path("login")
    @Produces("application/json")
    @Consumes({"application/json"})
    public SimpleClient login(JsonObject json) {
        System.out.println("json-> " + json.toString());

        if(!json.containsKey("email") || !json.containsKey("password")) {
            throw new WebApplicationException("{\"error\": \"wrong credentials\"}", 404);
           //return "{\"error\": \"wrong credentials\"}";
            //return false;
        }
        
        
        int res = storeBean.validateClient(json.getString("email"), json.getString("password"));
        System.out.println("--> Client id " + res);
        if(res >= 0)
        {
            SimpleClient c = storeBean.getClient(res);
            return c;
        }
        else throw new WebApplicationException("{\"error\": \"wrong credentials\"}", 404);
        
        //TODO: LOGIN call EJB funtion
        //return true;
        //return "{\"id\":\"C001\",\"name\":\"Manuel Pereira\",\"tax_id\":\"177142430\",\"email\":\"manu@gmail.com\",\"street\":\"Rua das Flores N 123\",\"city\":\"Porto\",\"zip_code1\":\"4100\",\"zip_code2\":\"000\"}";
    }
    
    /**
     * Register a user
     * @param user details
     * @return user id
     */
    @POST
    @Produces("application/json")
    @Consumes({"application/json"})
    public String register(JsonObject json) {
        System.out.println("json-> " + json.toString());

        if (!json.containsKey("name") || !json.containsKey("email") 
                || !json.containsKey("password")
                || !json.containsKey("city")) {
            throw new WebApplicationException("{\"error\": \"wrong parameters\"}", 400);
        }
        
        //TODO adress
        int res = storeBean.newClient(json.getString("name"), json.getString("city"), json.getString("email"));
        
        storeBean.changeClientPassword(json.getString("password"), res);
        
        //return id;
        return "{\"id\":"+ res +"}";
    }
    
    
    private StoreBeanRemote lookupStoreBeanRemote() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StoreBeanRemote) c.lookup("java:global/Application/Application-ejb/StoreBean!applicationejbAPI.StoreBeanRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}