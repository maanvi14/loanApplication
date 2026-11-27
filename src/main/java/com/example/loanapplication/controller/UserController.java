package com.example.loanapplication.controller;

import com.example.loanapplication.dto.CreateUserRequest;
import com.example.loanapplication.dto.UserResponse;
import com.example.loanapplication.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
/*
 * @RestController tells Spring that this class handles HTTP requests.
 * It also converts returned Java objects into JSON responses.
 */
@RequestMapping("/users")
/*
 * Common base URL for all endpoints in this controller.
 * For example, this controller handles URLs beginning with /users.
 */
public class UserController {

    // Controller delegates business logic to UserService.
    private final UserService userService;

    /*
     * Constructor injection:
     * Spring automatically provides the UserService bean.
     * The controller does not create the service object itself.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * POST /users
     *
     * Used to create a new user.
     *
     * @RequestBody converts incoming JSON into a
     * CreateUserRequest Java object using Jackson.
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody CreateUserRequest request) {

        // Delegate user creation and database operations to the service layer.
        UserResponse response = userService.createUser(request);

        // Return the created user's details with HTTP status 201 (Created).
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /*
     * GET /users/{id}
     *
     * Used to fetch a user using their ID.
     *
     * @PathVariable extracts the ID from the URL.
     * Example: /users/1 gives id = 1.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        // Ask UserService to find the user and prepare the response.
        UserResponse response = userService.getUserById(id);

        // Return the user details with HTTP status 200 (OK).
        return ResponseEntity.ok(response);
    }
}
