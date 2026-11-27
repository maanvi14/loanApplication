package com.example.loanapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/*
 * CONFIGURATION CLASS:
 *
 * Defines the RestClient bean used by loan-application to communicate with the separate user-service application.
 */
@Configuration //tells Spring that this class contains configuration.
public class UserServiceClientConfig {

    /*
     * Creates a RestClient configured with the base URL of the User Service.
     *
     * Other classes can use this bean to send HTTP requests to user-service.
     */
    @Bean //tells Spring to create and manage the returned object.
    public RestClient userServiceRestClient() { //Spring's HTTP client, which lets one Spring Boot application call another application through REST APIs.

        return RestClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }
}
