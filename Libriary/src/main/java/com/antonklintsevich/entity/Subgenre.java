package com.antonklintsevich.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "Subgenre")
@Table(name = "subgenre")
public class Subgenre extends AbstractEntity {
    @Id
    @Column(name = "subgenre_id")
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @Column(name = "subgenre_name")
    private String subgenrename;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getSubgenrename() {
        return subgenrename;
    }

    public void setSubgenrename(String subgenrename) {
        this.subgenrename = subgenrename;
    }
}
