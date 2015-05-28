/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import applicationejbAPI.StoreBeanRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author augusto
 */
@Path("books")
public class Book {
    StoreBeanRemote storeBean = lookupStoreBeanRemote();

    
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Book
     */
    public Book() {
    }

    /**
     * Returns all the books
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getBooks() {
        System.out.println("Returning all books");
        return "{\"books\":[{\"isbn\":\"9780743258234\",\"title\":\"Diffusion of Innovations, 5th Edition\",\"image\":\"http://bks0.books.google.pt/books/content?id=9U1K5LjUOwEC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"price\":79.95},{\"isbn\":\"9780743258233\",\"title\":\"Diffusion of Innovations, 5th Edition\",\"image\":\"http://bks0.books.google.pt/books/content?id=9U1K5LjUOwEC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"price\":79.95},{\"isbn\":\"9780743258232\",\"title\":\"Diffusion of Innovations, 5th Edition\",\"image\":\"http://bks0.books.google.pt/books/content?id=9U1K5LjUOwEC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"price\":79.95}]}";
    }
    
    /**
     * Return a book by isbn
     * @param isbn
     * @return 
     */
    @GET
    @Path("{isbn}")
    @Produces("application/json")
    public String getBook(@PathParam("isbn") String isbn) {
        //TODO GET the book with that ISBN and send in the right format
        System.out.println("Returning book w/ isbn: " + isbn);
        return "{\"bookshop\":{\"stock\":15},\"google\":{\"kind\":\"books#volumes\",\"totalItems\":1,\"items\":[{\"kind\":\"books#volume\",\"id\":\"9U1K5LjUOwEC\",\"etag\":\"84sfZRAeBnQ\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/9U1K5LjUOwEC\",\"volumeInfo\":{\"title\":\"Diffusion of Innovations, 5th Edition\",\"authors\":[\"Everett M. Rogers\"],\"publisher\":\"Simon and Schuster\",\"publishedDate\":\"2003-08-16\",\"description\":\"Now in its fifth edition, Diffusion of Innovations is a classic work on the spread of new ideas. In this renowned book, Everett M. Rogers, professor and chair of the Department of Communication & Journalism at the University of New Mexico, explains how new ideas spread via communication channels over time. Such innovations are initially perceived as uncertain and even risky. To overcome this uncertainty, most people seek out others like themselves who have already adopted the new idea. Thus the diffusion process consists of a few individuals who first adopt an innovation, then spread the word among their circle of acquaintances—a process which typically takes months or years. But there are exceptions: use of the Internet in the 1990s, for example, may have spread more rapidly than any other innovation in the history of humankind. Furthermore, the Internet is changing the very nature of diffusion by decreasing the importance of physical distance between people. The fifth edition addresses the spread of the Internet, and how it has transformed the way human beings communicate and adopt new ideas.\",\"industryIdentifiers\":[{\"type\":\"ISBN_13\",\"identifier\":\"9780743258234\"},{\"type\":\"ISBN_10\",\"identifier\":\"0743258231\"}],\"readingModes\":{\"text\":true,\"image\":false},\"pageCount\":576,\"printType\":\"BOOK\",\"categories\":[\"Social Science\",\"Social Science1\",\"Social nabo\"],\"averageRating\":4.0,\"ratingsCount\":4,\"maturityRating\":\"NOT_MATURE\",\"allowAnonLogging\":false,\"contentVersion\":\"0.3.4.0.preview.2\",\"imageLinks\":{\"smallThumbnail\":\"http://bks0.books.google.pt/books/content?id=9U1K5LjUOwEC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\"thumbnail\":\"http://bks0.books.google.pt/books/content?id=9U1K5LjUOwEC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"},\"language\":\"en\",\"previewLink\":\"http://books.google.pt/books?id=9U1K5LjUOwEC&pg=PA27&dq=isbn9780743258234&hl=&cd=1&source=gbs_api\",\"infoLink\":\"http://books.google.pt/books?id=9U1K5LjUOwEC&dq=isbn9780743258234&hl=&source=gbs_api\",\"canonicalVolumeLink\":\"http://books.google.pt/books/about/Diffusion_of_Innovations_5th_Edition.html?hl=&id=9U1K5LjUOwEC\"},\"saleInfo\":{\"country\":\"PT\",\"saleability\":\"FOR_SALE\",\"isEbook\":true,\"listPrice\":{\"amount\":37.65,\"currencyCode\":\"EUR\"},\"retailPrice\":{\"amount\":37.65,\"currencyCode\":\"EUR\"},\"buyLink\":\"http://books.google.pt/books?id=9U1K5LjUOwEC&dq=isbn9780743258234&hl=&buy=&source=gbs_api\",\"offers\":[{\"finskyOfferType\":1,\"listPrice\":{\"amountInMicros\":3.765E7,\"currencyCode\":\"EUR\"},\"retailPrice\":{\"amountInMicros\":3.765E7,\"currencyCode\":\"EUR\"}}]},\"accessInfo\":{\"country\":\"PT\",\"viewability\":\"PARTIAL\",\"embeddable\":true,\"publicDomain\":false,\"textToSpeechPermission\":\"ALLOWED_FOR_ACCESSIBILITY\",\"epub\":{\"isAvailable\":true,\"acsTokenLink\":\"http://books.google.pt/books/download/Diffusion_of_Innovations_5th_Edition-sample-epub.acsm?id=9U1K5LjUOwEC&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"},\"pdf\":{\"isAvailable\":false},\"webReaderLink\":\"http://books.google.pt/books/reader?id=9U1K5LjUOwEC&hl=&printsec=frontcover&output=reader&source=gbs_api\",\"accessViewStatus\":\"SAMPLE\",\"quoteSharingAllowed\":false},\"searchInfo\":{\"textSnippet\":\"Everett M. Rogers. Opinion leadership is the degree to which an individual is \\u003cbr\\u003e\\nable to influence other individuals&#39; attitudes or overt behavior informally in a \\u003cbr\\u003e\\ndesired way with relative frequency. This informal leadership is not a function of \\u003cbr\\u003e\\nthe&nbsp;...\"}}]}}";
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
