package com.example.loanapplication.user.service;

import com.example.loanapplication.user.dto.CreateUserRequest;
import com.example.loanapplication.user.dto.UserResponse;
import com.example.loanapplication.user.entity.User;
import com.example.loanapplication.user.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/*
 * MILESTONE 1: USER MANAGEMENT
 * Step 4: Create User Service
 * The Service layer contains the business logic of the application.
 *
 * It acts as a bridge between the Controller and Repository.
 *
 * Application flow:
 * Controller -> Service -> Repository -> Database
 *
 * @Service registers this class as a Spring-managed bean.
 * Spring discovers it through component scanning.
 */

@Service //@Service is a specialization of @Component.
public class UserService {
    /* UserRepository is a dependency of UserService.
     *  Dependency required by this service to interact with the database as UserRepository is responsible for database operations.
     *  We inject it through the constructor instead of creating its object manually using new.
     *  final ensures that this dependency cannot be reassigned after the constructor initializes it.
     */
    private final UserRepository userRepository;

    /*Constructor Injection: Spring automatically injects the UserRepository bean here by class constructor.
     * We do not create the repository using new because Spring Data JPA provides its runtime implementation and manages the bean.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /* Creates a new user
     * @Transactional allows Spring to manage the database transaction.
     * If the operation completes successfully, the transaction commits.
     * By default, an unchecked exception causes the transaction to roll back.
     */
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        /*
         * Step1: Convert the incoming CreateUserRequest DTO into a User entity.
         *
         * The getter methods retrieve the values stored in the request DTO.
         * These values are passed to the User constructor, which assigns them to the corresponding fields of the new User entity.
         *
         * We create an Entity because UserRepository works with User entities,not with CreateUserRequest DTOs.
         */
        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPhoneNumber()
        );


        /*
         * Step 2: Save the User entity.
         * save() is inherited from JpaRepository.
         * Spring Data JPA provides its implementation at runtime.
         */
        User savedUser = userRepository.save(user);

        /*
         * Step 3: Convert the saved Entity into a Response DTO.
         * We return UserResponse instead of exposing the User entity directly to the client.
         * This keeps our API response separate from our database structure.
         */
        UserResponse response = new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                savedUser.getCreatedAt()
        );

        // Return the response DTO to the Controller.
        return response;
    }
    /*
     * GET USER BY ID
     * This method retrieves an existing user from the database
     * using the user's unique ID and returns their details.
     * Example:
     * If the Controller calls getUserById(2L), this method
     * fetches the user whose primary key is 2.
     * readOnly = true indicates that this transaction is intended only for reading data. We are not creating, updating or deleting any user in this method.
     */
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id){
        /*
         * STEP 1: FETCH THE USER FROM THE DATABASE
         *
         * userRepository extends JpaRepository<User, Long>.
         * Therefore, Spring Data JPA already provides the findById() method. We do not need to write the SQL query ourselves.
         * findById(id) searches the User table using the primary key.
         * findById() returns Optional<User>, not directly User.
         * Why Optional?
         * Because the requested ID might exist in the database,
         * or there might be no user with that ID.
         * For example:
         * - If ID = 2 exists, Optional contains the User object.
         * - If ID = 100 does not exist, Optional is empty.
         *
         * orElseThrow() handles these two possibilities:
         * - If a User exists, it extracts and returns that User object.
         * - If no User exists, it throws the RuntimeException with the specified error message.
         *
         * The lambda expression () -> is used to provide the
         * exception that should be created if the Optional is empty.
         */
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with ID: " + id)
                );

        /*
         * STEP 2: CONVERT THE USER ENTITY INTO A RESPONSE DTO
         *
         * At this point, we have successfully retrieved a User object.
         *
         * However, User is our JPA Entity, which represents the structure of the data stored in the database.
         *
         * We do not directly return this Entity from the API.
         * Instead, we create a UserResponse DTO.
         *
         * WHY?
         * The Response DTO controls exactly which fields are sent back to the client. This prevents us from exposing the entire internal database Entity.
         * The getters retrieve values from the User Entity.
         * These values are passed into the UserResponse constructor.
         * This creates a separate DTO object containing the information that we want to return to the client.
         *
         * This process is called Entity-to-DTO mapping.
         */
        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedAt()
        );

        /*
         * STEP 3: RETURN THE RESPONSE DTO
         *
         * We return the UserResponse object to the Controller.
         * The Controller will send this object as the API response.
         * Spring automatically converts the Java object into JSON because the Controller uses @RestController.
         * The frontend receives the user's details in JSON format.
         */
        return response;

    }
}
