package com.antonklintsevich.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Genre")
@Table(name = "genre")
public class Genre extends AbstractEntity {
    @Id
    @Column(name = "genre_id")
    @GeneratedValue
    private Long id;
    @Column(name = "genre_name")
    private String genrename;
    @OneToMany(mappedBy="genre")
    private List<Subgenre> subgenres = new ArrayList<>();
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenrename() {
        return genrename;
    }

    public void setGenrename(String genrename) {
        this.genrename = genrename;
    }

    public List<Subgenre> getSubgenres() {
        return subgenres;
    }

    public void setSubgenres(List<Subgenre> subgenres) {
        this.subgenres = subgenres;
    }
}
