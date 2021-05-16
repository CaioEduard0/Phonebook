package com.example.phonebook.test.controllers;

import static com.example.phonebook.test.utils.UserCreator.createUserToBeSaved;
import static com.example.phonebook.test.utils.UserCreator.createUserToError;
import static com.example.phonebook.test.utils.UserCreator.createUserToUpdate;
import static com.example.phonebook.test.utils.UserCreator.createValidUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.phonebook.controllers.UserController;
import com.example.phonebook.dto.UserCreatorDTO;
import com.example.phonebook.dto.UserDTO;
import com.example.phonebook.entities.User;
import com.example.phonebook.services.UserService;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class UserControllerTest {
	
	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserService userServiceMock;
	
	private Validator validator;
	
	@BeforeEach
	void setUp() {
		
		when(userServiceMock.findUserById(anyLong())).thenReturn(createValidUser());
		
		when(userServiceMock.findUserByEmail(anyString())).thenReturn(createValidUser());
		
		when(userServiceMock.insertUser(any(User.class))).thenReturn(createValidUser());
		
		doNothing().when(userServiceMock).updateUser(anyLong(), any(User.class));
		
		doNothing().when(userServiceMock).deleteUser(anyLong());
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	void findUserById_ReturnsUser_WhenSuccessful() {
		User user = createValidUser();
		ResponseEntity<UserDTO> response = userController.findUserById(1L);
		
		assertThat(response).isNotNull();
		assertThat(response.getBody().getName()).isEqualTo(user.getName());
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	void findUserByEmail_ReturnsUser_WhenSuccessful() {
		User user = createValidUser();
		ResponseEntity<UserDTO> response = userController.findUserByEmail("xxx");
		
		assertThat(response).isNotNull();
		assertThat(response.getBody().getEmail()).isEqualTo(user.getEmail());
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	void insertUser_SavesUser_WhenSuccessful() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));	
		ResponseEntity<Void> response = userController.insertUser(createUserToBeSaved());
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCodeValue()).isEqualTo(201);
		assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/1");
	}
	
	@Test
	void updateUser_UpdatesUser_WhenSuccessful() {
		ResponseEntity<Void> response = userController.updateUser(1L, createUserToUpdate());
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	void deleteUser_RemovesUser_WhenSuccessful() {
		ResponseEntity<Void> response = userController.deleteUser(1L);
		
		assertThat(response).isNotNull();
		assertThat(response.getStatusCodeValue()).isEqualTo(204);
	}
	
	@Test
	void beanValidation_ReturnsErrors_WhenTheFieldsAreNotRight() {
		Set<ConstraintViolation<UserCreatorDTO>> violations = validator.validate(createUserToError());
		
		assertThat(violations).isNotEmpty();
		assertThat(violations.size()).isEqualTo(4);
	}
}
