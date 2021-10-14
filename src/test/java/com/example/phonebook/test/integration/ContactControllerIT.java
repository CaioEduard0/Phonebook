package com.example.phonebook.test.integration;

import static com.example.phonebook.test.utils.ContactCreator.createContact;
import static com.example.phonebook.test.utils.ContactCreator.createContactToBeSaved;
import static com.example.phonebook.test.utils.ContactCreator.createContactToUpdate;
import static com.example.phonebook.test.utils.UserCreator.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.entities.Contact;
import com.example.phonebook.entities.User;
import com.example.phonebook.repositories.ContactRepository;
import com.example.phonebook.repositories.UserRepository;
import com.example.phonebook.wrapper.PageableResponse;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ContactControllerIT {
	
	@Autowired
	@Qualifier(value = "roleUserAdmin")
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
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
		User user = createUser();
		Contact contact = createContact();
		contact.setUser(user);
		userRepository.save(user);
		contactRepository.save(contact);		
	}
	
	@Test
	void findAllUserContacts_ReturnsListOfContacts_WhenSuccessful() {	
		ResponseEntity<PageableResponse<ContactDTO>> response = testRestTemplate.exchange("/users/{id}/contacts", HttpMethod.GET, null,
				new ParameterizedTypeReference<PageableResponse<ContactDTO>>() {} , 1);
		
		assertThat(response).isNotNull();
		assertThat(response.getBody().toList().get(0).getName()).isEqualTo(createContact().getName());
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	void findContactsByName_ReturnsListOfContacts_WhenSuccessful() {
		ResponseEntity<PageableResponse<ContactDTO>> response = testRestTemplate.exchange("/users/{id}/contacts/find?name="+createContact().getName(),
				HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<ContactDTO>>() {}, 1);
		
		assertThat(response).isNotNull();
		assertThat(response.getBody().toList().get(0).getName()).isEqualTo(createContact().getName());
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	void insertContact_SavesContact_WhenSuccessful() {
		ResponseEntity<Void> response = testRestTemplate.exchange("/users/{id}/contacts", HttpMethod.POST, 
				new HttpEntity<>(createContactToBeSaved()), Void.class, 1);
		List<ContactDTO> savedContact = findContact(1);
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCodeValue()).isEqualTo(201);
		assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/users/1/contacts/2");
		assertThat(savedContact.get(0).getName()).isEqualTo(createContactToBeSaved().getName());
	}
	
	@Test
	void updateContact_UpdatesContact_WhenSuccessful() {
//		ResponseEntity<Void> response = testRestTemplate.exchange("/users/{userId}/contacts/{contactId}", HttpMethod.PUT,
//				new HttpEntity<>(createContactToUpdate().contactToDto(createContactToUpdate())), Void.class, 1, 1);
//		List<ContactDTO> updatedContact = findContact(1);
//
//		assertThat(response).isNotNull();
//		assertThat(response.getStatusCodeValue()).isEqualTo(200);
//		assertThat(updatedContact.get(0).getName()).isEqualTo(createContactToUpdate().getName());
	}
	
	@Test
	void deleteContact_RemovesContact_WhenSuccessful() {
		ResponseEntity<Void> response = testRestTemplate.exchange("/users/{userId}/contacts/{contactId}", HttpMethod.DELETE, null, Void.class, 1, 1);
		List<ContactDTO> deletedContact = findContact(1);
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCodeValue()).isEqualTo(204);
		assertThat(deletedContact).isEmpty();
	}
	
	private List<ContactDTO> findContact(int id) {
		ResponseEntity<PageableResponse<ContactDTO>> response = testRestTemplate.exchange("/users/{id}/contacts", HttpMethod.GET, null,
				new ParameterizedTypeReference<PageableResponse<ContactDTO>>() {} , id);
		return response.getBody().toList();
	}	
}
