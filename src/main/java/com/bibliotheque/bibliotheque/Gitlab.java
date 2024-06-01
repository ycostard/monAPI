package com.bibliotheque.bibliotheque;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Gitlab {

    private RestTemplate restTemplate;
    
    public void getUsers() {

        ResponseEntity<GitlabUser[]> responses = restTemplate.getForEntity("https://gitlab.com/api/v4/users", GitlabUser[].class);
        System.out.println(responses.getBody().length);
    }
}
