package com.antonklintsevich.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.annotations.TokenFilterDef;

@Entity(name = "Book")
@Indexed

@Table(name = "book")
public class Book extends AbstractEntity {

    public Book() {
    };

    public Book(String name, String author, Date dateAdd, Set<Subgenre> subgenres, int numberOfPages) {
        this.bookname = name;
        this.author = author;
        this.dateAdd = dateAdd;
        this.numberOfPages = numberOfPages;
        this.subgenres = subgenres;
    };

    private Long id;
    @Field(termVector = TermVector.YES)
    private String bookname;
    @Field(termVector = TermVector.YES)
    private String author;
    private Date dateAdd;
    private BigDecimal price;
    private Set<Subgenre> subgenres = new HashSet<Subgenre>();
    private int numberOfPages;

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Column(name = "bookname")
    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "book_subgenre", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "subgenre_id"))
    public Set<Subgenre> getSubgenres() {
        return subgenres;
    }

    public void setSubgenres(Set<Subgenre> subgenres) {
        this.subgenres = subgenres;
    }

    public void addSubgenre(Subgenre subgenre) {
        this.subgenres.add(subgenre);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}