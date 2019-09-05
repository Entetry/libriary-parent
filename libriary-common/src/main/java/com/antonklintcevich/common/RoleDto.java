package com.antonklintcevich.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleDto implements Serializable {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String rolename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

}
