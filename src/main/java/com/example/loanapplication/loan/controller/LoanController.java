package com.example.loanapplication.loan.controller;

import com.example.loanapplication.loan.dto.LoanApplicationRequest;
import com.example.loanapplication.loan.dto.LoanApplicationResponse;
import com.example.loanapplication.loan.entity.LoanApplication;
import com.example.loanapplication.loan.service.LoanApprovalService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * CONTROLLER LAYER: LoanController handles incoming HTTP requests related to loan applications.
 *
 * It receives loan details from the client, passes them to the service layer for processing, and returns the appropriate HTTP response.
 *
 * It acts as the entry point between the client (Postman/frontend) and the backend loan module.
 */

/*
 * @RestController: Marks this class as a REST controller managed by Spring.
 * WHY: We use it to handle HTTP requests and return data, usually in JSON format.
 * HOW: It combines @Controller and @ResponseBody.
 * Spring automatically converts returned Java objects into JSON using an HTTP message converter, typically Jackson.
 */
@RestController

/*
 * @RequestMapping("/loans"): Defines the common URL path for all endpoints in this controller.
 * WHY: Keeps all loan-related APIs under a common URL.
 * HOW: Every endpoint in this controller starts with /loans.
 * For example, @PostMapping creates the endpoint POST /loans.
 */
@RequestMapping("/loans")
public class LoanController {
    /*
     * Dependency on the service layer.
     * The controller uses LoanApprovalService to execute loan-related business logic.
     *
     * final ensures that this dependency is assigned once through the constructor and cannot be reassigned afterwards.
     */
    private final LoanApprovalService loanApprovalService;

    /*
     * CONSTRUCTOR INJECTION:
     *
     * Spring automatically provides the LoanApprovalService bean when creating the LoanController object.
     *
     * LoanApprovalService is registered as a Spring bean because its class is annotated with @Service.
     *
     * Constructor injection makes dependencies explicit and ensures that the controller receives the required service.
     */
    public LoanController(LoanApprovalService loanApprovalService) {
        this.loanApprovalService = loanApprovalService;
    }

    /*
     * @PostMapping: Maps HTTP POST requests to this method.
     * WHY: POST is commonly used to create or submit a new resource.
     */
    @PostMapping
    public ResponseEntity<LoanApplicationResponse> createLoanApplication(

            /*
             * @RequestBody: Tells Spring to read the JSON data from the HTTP request body.
             *
             * WHY:The client sends loan details as JSON, and we need those details inside a Java object to process them.
             *
             * HOW: Spring uses an HTTP message converter, typically Jackson to deserialize the JSON into a LoanApplicationRequest object.
             */
            @RequestBody LoanApplicationRequest request) {

        /*
         * STEP 1: Convert Request DTO into Entity.
         *
         * The request DTO contains the data received from the client.
         * We create a LoanApplication entity because the service and repository work with the entity.
         */
        LoanApplication application = new LoanApplication();

        application.setUserId(request.getUserId());
        application.setLoanAmount(request.getLoanAmount());
        application.setAnnualIncome(request.getAnnualIncome());
        application.setCreditScore(request.getCreditScore());

        /*
         * STEP 2: Call the Service Layer.
         *
         * The service evaluates the loan using business rules, sets its approval status, and saves it through the repository.
         *
         * The saved entity is returned with its generated database ID.
         */
        LoanApplication savedApplication =
                loanApprovalService.submitLoanApplication(application);

        /*
         * STEP 3: Convert Entity into Response DTO.
         *
         * We create a response DTO to control which fields are returned to the client instead of exposing the entire entity.
         */
        LoanApplicationResponse response = new LoanApplicationResponse();

        response.setId(savedApplication.getId());
        response.setUserId(savedApplication.getUserId());
        response.setLoanAmount(savedApplication.getLoanAmount());
        response.setStatus(savedApplication.getStatus());

        /*
         * STEP 4: Return the HTTP Response.
         *
         * ResponseEntity:
         * Represents the complete HTTP response, including its body and status code.
         *
         * HttpStatus.CREATED: Represents HTTP 201 Created, indicating that the resource was successfully created.
         *
         * The response DTO becomes the JSON response body.
         */
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
