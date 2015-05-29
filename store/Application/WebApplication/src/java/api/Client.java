/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package api;

import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author tiago
 */
@Path("client")
public class Client {
    
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
    public String putJson(@PathParam("id") String id, JsonObject json) {
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
            return "{\"error\": \"invalid request\"}";
        
        //Fake data for now
        return "{\"id\":\"C001\",\"name\":\"Manuel Pereira\",\"tax_id\":\"177142430\",\"email\":\"manu@gmail.com\",\"street\":\"Rua das Flores N 123\",\"city\":\"Porto\",\"zip_code1\":\"4100\",\"zip_code2\":\"000\"}";
    }
    
    @POST
    @Path("login")
    @Produces("application/json")
    @Consumes({"application/json"})
    public String login(JsonObject json) {
        System.out.println("json-> " + json.toString());

        if(!json.containsKey("email") || !json.containsKey("password")) {
           return "{\"error\": \"wrong credentials\"}";
            //return false;
        }
        
        //TODO: LOGIN call EJB funtion
        //return true;
        return "{\"id\":\"C001\",\"name\":\"Manuel Pereira\",\"tax_id\":\"177142430\",\"email\":\"manu@gmail.com\",\"street\":\"Rua das Flores N 123\",\"city\":\"Porto\",\"zip_code1\":\"4100\",\"zip_code2\":\"000\"}";
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
                || !json.containsKey("password") || !json.containsKey("street") 
                || !json.containsKey("city") || !json.containsKey("zip_code1") 
                || !json.containsKey("zip_code2")) {
            return "{\"error\": \"wrong parameters\"}";
        }

        //TODO: LOGIN call EJB funtion
        //return id;
        return "{\"id\":\"C022\"}";
    }
    
    
}