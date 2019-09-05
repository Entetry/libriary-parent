package com.antonklintcevich.common;

public enum FilterType {
    MORE(">"), LESS("<"), EQUALLY("="), LIKE("like");
    public final String type;

    private FilterType(String type) {
        this.type = type;
    }
    public String getFilterType() {
        return this.type;
    }
}
