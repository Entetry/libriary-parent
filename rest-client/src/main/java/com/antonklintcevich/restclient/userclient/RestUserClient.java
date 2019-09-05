package com.antonklintcevich.restclient.userclient;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.antonklintcevich.common.GenreDto;
import com.antonklintcevich.common.UserDto;
@Service
public class RestUserClient implements UserDetailsService {
 private final RestTemplate restTemplate;
    
    @Autowired
    public RestUserClient(RestTemplate template) {
        this.restTemplate = template;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String resourceUrl = "http://localhost:9977/users/userdetails";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(resourceUrl)
                .queryParam("username", username);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<UserDetails> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                entity, new ParameterizedTypeReference<UserDetails>() {
                });
        UserDetails userDetails=response.getBody();
        return userDetails;
    }
    public List<UserDto> getAllUsers(){
        String resourceUrl = "http://localhost:9977/users";
        ResponseEntity<List<UserDto>> response = restTemplate.exchange(resourceUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<UserDto>>() {
                });
        return response.getBody();
    }

}
