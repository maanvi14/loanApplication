package com.example.loanapplication.user.controller;

import com.example.loanapplication.user.dto.CreateUserRequest;
import com.example.loanapplication.user.dto.UserResponse;
import com.example.loanapplication.user.service.UserService;

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
     * @RequestBody converts incoming JSON into a CreateUserRequest Java object using Jackson.
     */
    @PostMapping //tells spring boot that this method handles HTTP POST requests.
    public ResponseEntity<UserResponse> createUser( //ResponseEntity = the complete HTTP response, UserResponse = the data that will be returned to the client.
            @RequestBody CreateUserRequest request) { //@RequestBody tells Spring Boot to read the incoming JSON from the request body.Jackson converts this JSON into a Java object=Deserialization. The java object is stored in the request variable.

        //Now, request contains the name, email and password sent by the client.
        /*Here, the work is delegated.
        1.The controller passes the CreateUserRequest.java(request DTO)object to the service layer.
        2.The service interacts with entity and handles user creation and database operations.
        3.The service returns a UserResponse.java(response DTO)object.
        4.We store that returned object in the response variable.
        The controller does not directly interact with the database.
        */
        UserResponse response = userService.createUser(request);

        // Return the created user's details with HTTP status 201 (Created).
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /*
     * GET API in Spring Boot that fetches a user by their ID : GET /users/{id}
     *
     * Used to fetch a user using their ID.
     *
     * @PathVariable Long id : extracts the ID from the URL.
     * Example:GET /users/1 gives id = 1L.
     */
    @GetMapping("/{id}") //It tells spring boot that this method handles HTTP GET requests. /{id} : means the URL contains a dynamic value called id.
    public ResponseEntity<UserResponse> getUserById( //responseEntity = complete HTTP response, UserResponse = type of data that will be returned to the client.
            @PathVariable Long id) {

        // Ask UserService to find the user and prepare the response.So,basically userService.getUserById(id) calls the service layer to fetch the user.
        UserResponse response = userService.getUserById(id);

        // Return the user details with HTTP status 200 (OK).
        // ResponseEntity.ok() wraps the returned user data in an HTTP response with status code 200 OK.
        return ResponseEntity.ok(response);
    }
}
/*
We use the @PostMapping annotation to handle POST requests for creating a new user. The client sends user details in JSON format through the request body. Using @RequestBody, Spring Boot receives this JSON, and Jackson deserializes it into a CreateUserRequest Java object. The controller passes this object to the service layer, which handles the business logic and saves the user in the database. Finally, the service returns a UserResponse object, and we use ResponseEntity with HTTP status 201 Created to send the response back to the client.
We use the @GetMapping annotation to handle GET requests. When the client sends a request like /users/1, the @PathVariable annotation extracts the user ID from the URL. The controller passes this ID to the service layer, which handles fetching the user details. Finally, we use ResponseEntity.ok() to return the user details with an HTTP 200 OK status.
*/

