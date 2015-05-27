/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author augusto
 */
@Entity
@Table(name = "BOOKS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findByIsbn", query = "SELECT b FROM Book b WHERE b.isbn = :isbn"),
    @NamedQuery(name = "Book.findByStock", query = "SELECT b FROM Book b WHERE b.stock = :stock")})
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ISBN")
    private String isbn;
    @Column(name = "STOCK")
    private Integer stock;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "isbn")
    private Collection<BookOrder> bookOrderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "isbn")
    private Collection<BookSell> bookSellCollection;

    public Book() {
    }

    public Book(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @XmlTransient
    public Collection<BookOrder> getBookOrderCollection() {
        return bookOrderCollection;
    }

    public void setBookOrderCollection(Collection<BookOrder> bookOrderCollection) {
        this.bookOrderCollection = bookOrderCollection;
    }

    @XmlTransient
    public Collection<BookSell> getBookSellCollection() {
        return bookSellCollection;
    }

    public void setBookSellCollection(Collection<BookSell> bookSellCollection) {
        this.bookSellCollection = bookSellCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (isbn != null ? isbn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.isbn == null && other.isbn != null) || (this.isbn != null && !this.isbn.equals(other.isbn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "applicationejb.Book[ isbn=" + isbn + " ]";
    }
    
}
