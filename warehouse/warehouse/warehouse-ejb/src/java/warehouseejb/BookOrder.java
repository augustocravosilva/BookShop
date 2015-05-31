package warehouseejb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import warehouseinterface.IBookOrder;


public class BookOrder implements IBookOrder {
    private Integer id;
    private String isbn;
    private String bookName;
    private int quantity;
    private String orderDate;
    private String dispatchDate;
    private String status;
    
    public BookOrder() {
    }
    
    public BookOrder(Integer id) {
        this.id = id;
    }
    
    public BookOrder(Integer id, String isbn, String bookName, int quantity, String orderDate, String dispatchDate, String status) {
        this.id = id;
        this.isbn = isbn;
        this.bookName = bookName;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.dispatchDate = dispatchDate;
        this.status = status;
    }
    
    public BookOrder(String isbn, String bookName, int quantity, String orderDate) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }
    
    
    @Override
    public Integer getId() {
        return id;
    }
    
    @Override
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Override
    public String getIsbn() {
        return isbn;
    }
    
    @Override
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    @Override
    public String getBookName() {
        return bookName;
    }
    
    @Override
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    @Override
    public int getQuantity() {
        return quantity;
    }
    
    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String getStatus() {
        return status;
    }
    
    @Override
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String getOrderDate() {
        return orderDate;
    }
    
    @Override
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    
    @Override
    public String getDispatchDate() {
        return dispatchDate;
    }
    
    @Override
    public void setDispatchDate(String dispatchDate) {
        this.dispatchDate = dispatchDate;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookOrder)) {
            return false;
        }
        BookOrder other = (BookOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "warehouseejb.BookOrder[ id=" + id + " ]";
    }
    
    public void save() throws SQLException {
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        String query = String.format("UPDATE BookOrder SET isbn = \'%s\', bookName = \'%s\', "
                + "quantity = %d, orderDate = \'%s\', dispatchDate = \'%s\', status = \'%s\' WHERE id = %d", isbn, bookName, quantity, orderDate, dispatchDate, status, id);
        
        stmt.executeUpdate(query);
        close(conn);
    }
    
    public void create() throws SQLException {
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        String query = String.format("INSERT INTO BookOrder(isbn, bookName, quantity, orderDate, dispatchDate, status) VALUES (\'%s\', \'%s\', %d, \'%s\', \'%s\', \'%s\')", isbn, bookName, quantity, orderDate, null, "open");
        System.out.println(query);
        stmt.execute(query);
        close(conn);
    }
    
    static private Connection connect() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url = "jdbc:derby://localhost:1527/WarehouseDB";
            Connection conn = DriverManager.getConnection(url, "warehouseUser", "warehousePassword");
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    static private void close(Connection conn) {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static List<IBookOrder> findAll() throws SQLException {
        List<IBookOrder> booksList = new LinkedList<>();
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM BookOrder ORDER BY orderDate DESC";
        ResultSet rs = stmt.executeQuery(query);
        
        while(rs.next()) {
            IBookOrder bookOrder = new BookOrder();
            bookOrder.setId(rs.getInt("id"));
            bookOrder.setBookName(rs.getString("bookName"));
            bookOrder.setIsbn(rs.getString("isbn"));
            bookOrder.setQuantity(rs.getInt("quantity"));
            bookOrder.setStatus(rs.getString("status"));
            bookOrder.setOrderDate(rs.getString("orderDate"));
            bookOrder.setDispatchDate(rs.getString("dispatchDate"));
            booksList.add(bookOrder);
        }
        
        close(conn);
        
        return booksList;
    }
    
    
    static IBookOrder findById(int id) throws SQLException {
        List<IBookOrder> booksList = new LinkedList<>();
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM BookOrder WHERE id = " + id;
        ResultSet rs = stmt.executeQuery(query);
        
        IBookOrder bookOrder = null;
        
        if (rs.next()) {
            bookOrder = new BookOrder();
            bookOrder.setId(rs.getInt("id"));
            bookOrder.setBookName(rs.getString("bookName"));
            bookOrder.setIsbn(rs.getString("isbn"));
            bookOrder.setQuantity(rs.getInt("quantity"));
            bookOrder.setStatus(rs.getString("status"));
            bookOrder.setOrderDate(rs.getString("orderDate"));
            bookOrder.setDispatchDate(rs.getString("dispatchDate"));
        }
        
        close(conn);
        
        return bookOrder;
    }
}