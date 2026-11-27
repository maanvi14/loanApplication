package com.example.loanapplication.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import com.example.loanapplication.user.entity.User;

/*
 * CLIENT LAYER: UserServiceClient is responsible for communicating with the separate User Service microservice through HTTP.
 *
 * It keeps REST communication logic separate from the controller and business service layers.
 */

@Component
public class UserServiceClient {

    private final RestClient restClient;

    //Constructor Injection: Spring provides the RestClient bean configured in UserServiceClientConfig.
    public UserServiceClient(RestClient userServiceRestClient) {
        this.restClient = userServiceRestClient;
    }
    /*
     * Fetches a user from the User Service using their ID.
     *
     * GET request: http://localhost:8081/users/{id}
     *
     * RestClient sends the HTTP request and converts the JSON response into a Java User object.
     */
    public User getUserById(Long id) {

        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(User.class);
    }
}