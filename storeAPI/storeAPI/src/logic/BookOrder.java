/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author augusto
 */
@Entity
@Table(name = "BOOKORDERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BookOrder.findAll", query = "SELECT b FROM BookOrder b"),
    @NamedQuery(name = "BookOrder.findById", query = "SELECT b FROM BookOrder b WHERE b.id = :id"),
    @NamedQuery(name = "BookOrder.findByQuantity", query = "SELECT b FROM BookOrder b WHERE b.quantity = :quantity"),
    @NamedQuery(name = "BookOrder.findByClient", query = "SELECT b FROM BookOrder b WHERE b.clientid = :client"),
    @NamedQuery(name = "BookOrder.findByBook", query = "SELECT b FROM BookOrder b WHERE b.isbn = :isbn"),    
    @NamedQuery(name = "BookOrder.findByState", query = "SELECT b FROM BookOrder b WHERE b.state = :state")})
public class BookOrder implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDERDATE")
    @Temporal(TemporalType.DATE)
    private Date orderdate;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Size(max = 200)
    @Column(name = "STATE")
    private String state;
    @JoinColumn(name = "ISBN", referencedColumnName = "ISBN")
    @ManyToOne(optional = false)
    private Book isbn;
    @JoinColumn(name = "CLIENTID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Client clientid;

    public BookOrder() {
        orderdate = Calendar.getInstance().getTime();
    }

    public BookOrder(Integer id) {
        this.id = id;
        orderdate = Calendar.getInstance().getTime();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Book getIsbn() {
        return isbn;
    }

    public void setIsbn(Book isbn) {
        this.isbn = isbn;
    }

    public Client getClientid() {
        return clientid;
    }

    public void setClientid(Client clientid) {
        this.clientid = clientid;
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
        return "applicationejb.BookOrder[ id=" + id + " ]";
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }
    
}
