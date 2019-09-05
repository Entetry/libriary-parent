package com.antonklintcevich.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FilterData {
    @JsonProperty
    private String field;
    @JsonProperty
    private String value;
    @JsonProperty
    private FilterType filterType;
    public FilterData() {}
    public FilterData(String field,String value,FilterType filterType) {
        this.field=field;
        this.value=value;
        this.filterType=filterType;
    }
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }
}
