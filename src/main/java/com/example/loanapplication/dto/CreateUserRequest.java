package com.example.loanapplication.dto;
/*
 * MILESTONE 1: USER MANAGEMENT
 * Step 3: Create User Request DTO
 *
 * Represents the data received from the client when creating a new user.
 *
 * NOTE:
 * DTOs are not explicitly mentioned in the Month 2 assignment.Implemented additionally to separate incoming API request data from the JPA Entity.
 *
 * DTO -> A DTO(Data Transfer Object) is an object used to transfer data between different layers or systems without exposing the internal entity directly.
 * WHY?
 * 1. Security – Prevent exposure of sensitive fields.
 * 2. Separation – Decouple API structure from database structure.
 * 3. Validation – Control incoming data so we can rejet invalid data at the API boundary.
 * 4. Flexibility – Support different request/response structures.
 * 5. Maintainability – Reduce coupling between API and persistence layers.
   Request DTO: Controls what data the client can send.
   Response DTO: Controls what data the API returns.
  */

public class CreateUserRequest {
    //User's name received from the client.
    private String name;

    //User's email received from the client.
    private String email;

    //User's phone number received from the client.
    private String phoneNumber;

    /*
     * No-argument constructor.
     * Jackson uses this constructor to create an empty CreateUserRequest object when converting incoming JSON into a Java object (deserialization).
     * After creating the object, Jackson populates its fields using setters.
     */
    public CreateUserRequest() {}

    //Getter to retrieve user's name.
    public String getName() {
        return name;
    }

    //Setter to assign the name received from the client.
    public void setName(String name) {
        this.name = name;
    }

    //Getter to retrieve user's email.
    public String getEmail() {
        return email;
    }

    //Setter to assign the email received from the client.
    public void setEmail(String email) {
        this.email = email;
    }

    //Getter to retrieve user's phone number.
    public String getPhoneNumber() {
        return phoneNumber;
    }

    //Setter to assign the phone number received from the client.
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
