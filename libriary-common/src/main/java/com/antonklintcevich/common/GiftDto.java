package com.antonklintcevich.common;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GiftDto implements Serializable {
    @JsonProperty
    private String recipientName;
    @JsonProperty
    private Set<BookDto> bookDtos = new HashSet<>();

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Set<BookDto> getBookDtos() {
        return bookDtos;
    }

    public void setBookDtos(Set<BookDto> bookDtos) {
        this.bookDtos = bookDtos;
    }

    public void addBookDto(BookDto bookDto) {
        this.bookDtos.add(bookDto);
    }

}
