package com.example.loanapplication.entity;
/*
 * MILESTONE 1: USER MANAGEMENT
 * Step 1: Create User Entity
 *
 * Represents a User in the Loan Application.
 * Maps Java User objects to rows in the MySQL users table using JPA.
 */
import jakarta.persistence.*;
import java.time.LocalDateTime;

// Marks this class as a JPA entity.
@Entity
//Explicitly maps this entity to the "users" table.
@Table(name = "users")
public class User {

    //Primary key of the users table.
    @Id
    //MySQL generates the ID using auto-increment.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Name is mandatory and limited to 100 characters.
    @Column(nullable = false, length = 100)
    private String name;

    //Email is mandatory and must be unique.
    @Column(nullable = false, unique = true)
    private String email;

    //Phone number is optional, with a maximum length of 15.
    @Column(length = 15)
    private String phoneNumber;

    //Stores the date and time when the user is created.
    private LocalDateTime createdAt;

    /*
    Must have a public or protected no-argument constructor.
    Protected constructor required by JPA. Hibernate uses it to instantiate entity objects while fetching data from the database and may need it for proxy lazy-loading.Lazy Loading is a technique in Hibernate where related data is fetched from the database only when it is actually accessed, rather than loading everything immediately.
    Protected restricts direct object creation from unrelated classes while still allowing JPA/hibernate to access it.
    In protected, direct object creation from other classes are restricted but in Public it's allowed and u can't access it from any package , u can just do it through inheritance.So, Protected gives better Encapsulation than Public.*/
    protected User(){}

    //Parameterized Constructor for object creation
    public User(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    //Automatically sets creation timestamp before the entity is first inserted.
    //@PrePersist is a JPA lifecycle callback.
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    //Getter to retrieve the database-generated ID.
    public Long getId() {
        return id;
    }

    //Getter to retrieve user's name.
    public String getName() {
        return name;
    }

    //Setter to update user's name.
    public void setName(String name) {
        this.name = name;
    }

    //Getter for email.
    public String getEmail() {
        return email;
    }

    //Setter to update user's email.
    public void setEmail(String email) {
        this.email = email;
    }

    //Getter to retrieve user's phone number.
    public String getPhoneNumber() {
        return phoneNumber;
    }

    //Setter to update user's phone number.
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //Getter to retrieve the automatically generated creation timestamp.No setter to prevent arbitrary modification of createdAt.
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
