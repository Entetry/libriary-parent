package com.antonklintcevich.restclient.bookclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.antonklintcevich.common.BookDto;
import com.antonklintcevich.common.GenreDto;
import com.antonklintcevich.common.SearchParameters;

@Component
public class BookClient {
    
    private final RestTemplate restTemplate;
    
    @Autowired
    public BookClient(RestTemplate template) {
        this.restTemplate = template;
    }
    
    public List<BookDto> getAllBooks(SearchParameters searchParameters) {
        String resourceUrl = "http://localhost:9977/books";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<BookDto>> response = restTemplate.exchange(resourceUrl, HttpMethod.POST,
                new HttpEntity<>(searchParameters, headers), new ParameterizedTypeReference<List<BookDto>>() {
                });
        return response.getBody();
    }
    public List<GenreDto> getAllGenres(){
        String resourceUrl = "http://localhost:9977/books/genres";
        ResponseEntity<List<GenreDto>> response = restTemplate.exchange(resourceUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<GenreDto>>() {
                });
        return response.getBody();
    }
    public void deleteBook(Long id) {
        String resourceUrl = "http://localhost:9977/books/" + id ;
        restTemplate.delete(resourceUrl);
    }
    public void createBook(BookDto dto) {
        String resourceUrl = "http://localhost:9977/books/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(resourceUrl, HttpMethod.POST,
                new HttpEntity<>(dto, headers), new ParameterizedTypeReference<BookDto>() {
                });
    }
    public void updateBook(BookDto bookDto) {
        String resourceUrl = "http://localhost:9977/books";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<BookDto>> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT,
                new HttpEntity<>(bookDto, headers), new ParameterizedTypeReference<List<BookDto>>() {
                });
    }
}
