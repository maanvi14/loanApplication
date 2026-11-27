package com.example.loanapplication;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/*
 * @SpringBootTest:
 * Loads the Spring Boot application context with actual application beans.
 *
 * @AutoConfigureMockMvc:
 * Configures MockMvc to test REST APIs without starting a real HTTP server.
 *
 * @Transactional:
 * Runs the test inside a database transaction.
 * Changes made during the test are rolled back after the test finishes.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserIntegrationTest {

	/*
	 * @Autowired:
	 * Asks Spring to inject an already-created bean.
	 *
	 * MockMvc:
	 * Allows us to send HTTP requests (GET, POST, etc.)
	 * to our application during testing.
	 */
	@Autowired
	private MockMvc mockMvc;

	/*
	 * ObjectMapper:
	 * Converts JSON data into Java objects and vice versa.
	 * Here, we use it to read the JSON response and extract the user ID.
	 */
	@Autowired
	private ObjectMapper objectMapper;


	/*
	 * @Test:
	 * Tells JUnit that this method is a test case.
	 *
	 * shouldCreateAndFetchUser():
	 * Tests whether we can create a user and then fetch that same user.
	 */
	@Test
	void shouldCreateAndFetchUser() throws Exception {

		// Generate a unique email to avoid duplicate-email errors on repeated test runs.
		String email = "rahul" + UUID.randomUUID() + "@gmail.com";

		// JSON request body containing the details of the user we want to create.
		String requestBody = """
                {
                    "name": "Rahul",
                    "email": "%s",
                    "phoneNumber": "9876543210"
                }
                """.formatted(email);


		// STEP 1: Send a POST request to create a new user.
		String response = mockMvc.perform(post("/users")
						// Tells the API that we are sending JSON data.
						.contentType(MediaType.APPLICATION_JSON)

						// Attaches the user details as the request body.
						.content(requestBody))

				// Verify that the API returns HTTP 201 (Created).
				.andExpect(status().isCreated())

				// Get the response returned by the POST request.
				.andReturn()
				.getResponse()
				.getContentAsString();


		// STEP 2: Extract the generated user ID from the POST response.
		JsonNode json = objectMapper.readTree(response);

		// Read the "id" field from the JSON response and convert it into a long.
		long userId = json.get("id").asLong();


		// STEP 3: Send a GET request to fetch the newly created user.
		mockMvc.perform(get("/users/" + userId))

				// Verify that the API returns HTTP 200 (OK).
				.andExpect(status().isOk())

				// Verify that the fetched user's ID matches the generated ID.
				.andExpect(jsonPath("$.id").value(userId))

				// Verify that the fetched user's name is correct.
				.andExpect(jsonPath("$.name").value("Rahul"))

				// Verify that the fetched user's email is correct.
				.andExpect(jsonPath("$.email").value(email));
	}
}
