package com.stream.tour.domain.reservations.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

class ReservationEntityControllerTest {

    @Test
    void test() {
        UriComponents components = UriComponentsBuilder.fromUriString("http://localhost:8080/reservations/status")
                .queryParam("reservationIds", "1")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJwYXJ0bmVySWQiOjEsInN1YiI6InRlc3RAdGVzdC5jb20iLCJpYXQiOjE3MDgyMzQ1MDEsImV4cCI6MTcwODIzODEwMX0.0Cjg9siFyfqLXo-SuXd-uLacpLijfClEfqupHzZoDfk");

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> exchange = restTemplate.exchange(
                components.toUri(),
                HttpMethod.GET,
                entity,
                String.class
        );

        System.out.println(exchange);
    }

}