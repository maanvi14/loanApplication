
package com.example.loanapplication.user.repository;

import com.example.loanapplication.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * MILESTONE 1: USER MANAGEMENT
 * Step 2: Create User Repository
 *
 * Responsible for performing database operations on the User entity using Spring Data JPA.
 */

/*
 * JpaRepository provides built-in CRUD operations.
 *
 * Q: How does Spring create an implementation when we only write an interface?
 *
 * A: Spring Data JPA uses runtime proxy generation to provide an implementation of repository interfaces.
 *
 *    During application startup, it scans interfaces extending JpaRepository and creates proxy objects for them.
 *
 *    When we call a repository method, the proxy delegates the operation to SimpleJpaRepository, which uses EntityManager to perform database operations through Hibernate.
 *
 *    Here:
 *    User -> Entity class we want to manage.
 *    Long -> Datatype of the entity's primary key.
 */

public interface UserRepository extends JpaRepository<User, Long> {

    /*
     * No methods need to be implemented manually here.
     *
     * Spring Data JPA provides methods such as:
     *
     * save(user)          - Insert or update a user.
     * findById(id)        - Retrieve a user by primary key.
     * findAll()           - Retrieve all users.
     * deleteById(id)      - Delete a user by primary key.
     * existsById(id)      - Check whether a user exists.
     */
}

