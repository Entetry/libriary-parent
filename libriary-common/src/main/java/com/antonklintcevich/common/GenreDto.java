package com.antonklintcevich.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;

public class GenreDto implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String genrename;
    @JsonProperty
    private List<SubgenreDto> subgenres = new ArrayList<>();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genrename == null) ? 0 : genrename.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GenreDto other = (GenreDto) obj;
        if (genrename == null) {
            if (other.genrename != null)
                return false;
        } else if (!genrename.equals(other.genrename))
            return false;
        return true;
    }

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

    public List<SubgenreDto> getSubgenres() {
        return subgenres;
    }

    public void setSubgenres(List<SubgenreDto> subgenres) {
        this.subgenres = subgenres;
    }
}
