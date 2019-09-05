package com.antonklintcevich.restclient.orderclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.antonklintcevich.common.OrderDto;
import com.antonklintcevich.common.UserDto;
@Component
public class RestOrderClient {
 private final RestTemplate restTemplate;
    
    @Autowired
    public RestOrderClient(RestTemplate template) {
        this.restTemplate = template;
    }
    public List<OrderDto> getAllOrders() {
        String resourceUrl = "http://localhost:9977/orders";
        ResponseEntity<List<OrderDto>> response = restTemplate.exchange(resourceUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<OrderDto>>() {
                });
        return response.getBody();
    }
}
