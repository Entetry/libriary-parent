package com.antonklintcevich.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SortData implements Serializable {
    @JsonProperty
    private String name;
    @JsonProperty

    private String sortOrder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
