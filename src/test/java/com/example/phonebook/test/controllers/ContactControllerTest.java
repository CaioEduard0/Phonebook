//package com.example.phonebook.test.controllers;
//
//import static com.example.phonebook.test.utils.ContactCreator.createContactToBeSaved;
//import static com.example.phonebook.test.utils.ContactCreator.createContactToError;
//import static com.example.phonebook.test.utils.ContactCreator.createContactToUpdate;
//import static com.example.phonebook.test.utils.ContactCreator.createValidContact;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//import java.util.Set;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.example.phonebook.controllers.ContactController;
//import com.example.phonebook.dto.ContactDTO;
//import com.example.phonebook.services.ContactService;
//
//@ActiveProfiles("test")
//@ExtendWith(SpringExtension.class)
//class ContactControllerTest {
//
//	@InjectMocks
//	private ContactController contactController;
//
//	@Mock
//	private ContactService contactServiceMock;
//
//	private Validator validator;
//
//	@BeforeEach
//	void setUp() {
//
//		when(contactServiceMock.findAllContacts(anyLong())).thenReturn(Arrays.asList(createValidContact()));
//
//		when(contactServiceMock.findContactsByName(anyLong(), anyString())).thenReturn(Arrays.asList(createValidContact()));
//
//		when(contactServiceMock.insertContact(anyLong(), any(ContactDTO.class))).thenReturn(createValidContact());
//
//		doNothing().when(contactServiceMock).updateContact(anyLong(), anyLong(), any(ContactDTO.class));
//
//		doNothing().when(contactServiceMock).deleteContact(anyLong(), anyLong());
//
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		validator = factory.getValidator();
//	}
//
//	@Test
//	void findAllUserContacts_ReturnsListOfContacts_WhenSuccessful() {
//		//ResponseEntity<Page<ContactDTO>> response = contactController.findAllUserContacts(1L);
//
////		assertThat(response).isNotNull();
////		assertThat(response.getBody().toList().get(0).getName()).isEqualTo(createValidContact().getName());
////		assertThat(response.getStatusCodeValue()).isEqualTo(200);
//	}
//
//	@Test
//	void findAllUserContacts_ReturnsEmptyList_WhenUserDoNotHaveContacts() {
////		when(contactServiceMock.findAllContacts(anyLong())).thenReturn(Arrays.asList());
////		ResponseEntity<Page<ContactDTO>> response = contactController.findAllUserContacts(1L);
////
////		assertThat(response).isNotNull();
////		assertThat(response.getBody().toList()).isEmpty();
////		assertThat(response.getStatusCodeValue()).isEqualTo(200);
//	}
//
//	@Test
//	void findContactsByName_ReturnsListOfContacts_WhenSuccessful() {
////		ResponseEntity<Page<ContactDTO>> response = contactController.findContactsByName(1L, "xxx");
////
////		assertThat(response).isNotNull();
////		assertThat(response.getBody().toList().get(0).getPhone()).isEqualTo(createValidContact().getPhone());
////		assertThat(response.getStatusCodeValue()).isEqualTo(200);
//	}
//
//	@Test
//	void insertContact_SavesContact_WhenSuccessful() {
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//		ResponseEntity<Void> response = contactController.insertContact(1L, createContactToBeSaved());
//
//		assertThat(response).isNotNull();
//		assertThat(response.getStatusCodeValue()).isEqualTo(201);
//		assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/1");
//	}
//
//	@Test
//	void updateContact_UpdatesContact_WhenSuccessful() {
////		ResponseEntity<Void> response = contactController.updateContact(1L, 1L, createContactToUpdate().contactToDto(createContactToUpdate()));
////
////		assertThat(response).isNotNull();
////		assertThat(response.getStatusCodeValue()).isEqualTo(200);
//	}
//
//	@Test
//	void deleteContact_DeletesContact_WhenSuccessful() {
//		ResponseEntity<Void> response = contactController.deleteContact(1L, 1L);
//
//		assertThat(response).isNotNull();
//		assertThat(response.getStatusCodeValue()).isEqualTo(204);
//	}
//
//	@Test
//	void beanValidation_ReturnsErrors_WhenTheFieldsAreNotRight() {
//		Set<ConstraintViolation<ContactDTO>> violations = validator.validate(createContactToError());
//
//		assertThat(violations).isNotEmpty();
//		assertThat(violations.size()).isEqualTo(2);
//	}
//}
