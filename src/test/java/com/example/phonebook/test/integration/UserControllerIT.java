package com.example.phonebook.test.integration;

import static com.example.phonebook.test.utils.UserCreator.createUser;
import static com.example.phonebook.test.utils.UserCreator.createUserToBeDeleted;
import static com.example.phonebook.test.utils.UserCreator.createUserToBeSaved;
import static com.example.phonebook.test.utils.UserCreator.createUserToUpdate;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.phonebook.dto.UserDTO;
import com.example.phonebook.repositories.UserRepository;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerIT {
	
	@Autowired
	@Qualifier(value = "roleUserAdmin")
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	@Lazy
	@TestConfiguration
	static class Config {
		
		@Bean(name = "roleUserAdmin")
		public TestRestTemplate rolesUser(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:"+port).basicAuthentication("jose", "123456");
			return new TestRestTemplate(restTemplateBuilder);
		}
	}
	
	@BeforeEach
	void setUp() {
		userRepository.save(createUser());
	}
	
	@Test
	void findUserById_ReturnsUser_WhenSuccessful() {
		ResponseEntity<UserDTO> response = findUser(1L);
		
		assertThat(response).isNotNull();
		assertThat(response.getBody().getName()).isEqualTo(createUser().getName());
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	void findUserByEmail_ReturnsUser_WhenSuccessful() {
		ResponseEntity<UserDTO> response = testRestTemplate.exchange("/users/find?email={email}", HttpMethod.GET, null, UserDTO.class, createUser().getEmail());
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	void insertUser_SavesUser_WhenSuccessful() {
		ResponseEntity<Void> response = testRestTemplate.exchange("/users", HttpMethod.POST, new HttpEntity<>(createUserToBeSaved()), Void.class);
		ResponseEntity<UserDTO> savedUser = findUser(2L);
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCodeValue()).isEqualTo(201);
		assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/users/2");
		assertThat(savedUser.getBody().getName()).isEqualTo(createUserToBeSaved().getName());
	}
	
	@Test
	void updateUser_UpdatesUser_WhenSuccessful() {
		ResponseEntity<Void> response = testRestTemplate.exchange("/users/{id}", HttpMethod.PUT, new HttpEntity<>(createUserToUpdate()), Void.class, 1);
		ResponseEntity<UserDTO> updatedUser = findUser(1L);
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(updatedUser.getBody().getName()).isEqualTo(createUserToUpdate().getName());
	}
	
	@Test
	void deleteUser_RemovesUser_WhenSuccessful() {
		userRepository.save(createUser());
		userRepository.save(createUserToBeDeleted());
		ResponseEntity<Void> response = testRestTemplate.exchange("/users/{id}", HttpMethod.DELETE, null, Void.class, 2);
		userRepository.save(createUser());
		ResponseEntity<UserDTO> deletedUser = findUser(2L);

		assertThat(response).isNotNull();
		assertThat(response.getStatusCodeValue()).isEqualTo(204);
		assertThat(deletedUser.getStatusCodeValue()).isEqualTo(404);
	}
	
	private ResponseEntity<UserDTO> findUser(Long id) {
		return testRestTemplate.exchange("/users/{id}", HttpMethod.GET, null, UserDTO.class, id);
	}
}
