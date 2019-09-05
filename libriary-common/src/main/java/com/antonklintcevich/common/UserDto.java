package com.antonklintcevich.common;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto implements Serializable {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String firstname;
    @JsonProperty
    private String lastname;
    @JsonProperty
    private Date dob;
    @JsonProperty
    private String email;
    @JsonProperty
    private Set<BookDto> books;
    @JsonProperty
    private String password;
    @JsonProperty
    private Set<RoleDto> roles;
    @JsonProperty
    private String username;
    @JsonProperty
    private boolean enabled;
    @JsonProperty
    private WalletDto walletDto;
    @JsonProperty
    private UserStatusDto status;
    public UserDto(Long id, String username, String firstname, String lastname, Date dob, String email,
            String password) {
        this.id = id;
        this.dob = dob;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        // this.books = books;
        this.password = password;
        // this.roles=roles;
        this.username = username;
    }

    public UserDto() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<BookDto> getBooks() {
        return books;
    }

    public void setBooks(Set<BookDto> books) {
        this.books = books;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UserStatusDto getStatus() {
        return status;
    }

    public void setStatus(UserStatusDto status) {
        this.status = status;
    }

    public WalletDto getWalletDto() {
        return walletDto;
    }

    public void setWalletDto(WalletDto walletDto) {
        this.walletDto = walletDto;
    }
}
