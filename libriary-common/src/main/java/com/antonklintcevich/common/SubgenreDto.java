package com.antonklintcevich.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubgenreDto implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty
    private GenreDto genreDto;
    @JsonProperty
    private String subgenrename;
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subgenrename == null) ? 0 : subgenrename.hashCode());
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
        SubgenreDto other = (SubgenreDto) obj;
        if (subgenrename == null) {
            if (other.subgenrename != null)
                return false;
        } else if (!subgenrename.equals(other.subgenrename))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubgenrename() {
        return subgenrename;
    }

    public void setSubgenrename(String subgenrename) {
        this.subgenrename = subgenrename;
    }

    public GenreDto getGenreDto() {
        return genreDto;
    }

    public void setGenreDto(GenreDto genreDto) {
        this.genreDto = genreDto;
    }
}
