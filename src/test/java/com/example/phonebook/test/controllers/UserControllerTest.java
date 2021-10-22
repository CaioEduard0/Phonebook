//package com.example.phonebook.test.controllers;
//
//import com.example.phonebook.controllers.UserController;
//import com.example.phonebook.dto.UserDTO;
//import com.example.phonebook.dto.UserResponseDTO;
//import com.example.phonebook.dto.UserUpdateDTO;
//import com.example.phonebook.services.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//import java.util.Objects;
//import java.util.Set;
//
//import static com.example.phonebook.test.utils.UserCreator.createUserResponse;
//import static com.example.phonebook.test.utils.UserCreator.createUserToBeSaved;
//import static com.example.phonebook.test.utils.UserCreator.createUserToError;
//import static com.example.phonebook.test.utils.UserCreator.createUserToUpdate;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//@ActiveProfiles("test")
//@ExtendWith(SpringExtension.class)
//class UserControllerTest {
//
//	@InjectMocks
//	private UserController userController;
//
//	@Mock
//	private UserService userServiceMock;
//
//	private Validator validator;
//
//	@BeforeEach
//	void setUp() {
//
//		when(userServiceMock.find(anyLong())).thenReturn(createUserResponse());
//
//		when(userServiceMock.find(anyString())).thenReturn(createUserResponse());
//
//		when(userServiceMock.create(any(UserDTO.class))).thenReturn(createUserResponse());
//
//		doNothing().when(userServiceMock).update(any(UserUpdateDTO.class));
//
//		doNothing().when(userServiceMock).delete(anyLong());
//
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		validator = factory.getValidator();
//	}
//
//	@Test
//	void givenFind_WhenUserExists_ThenReturnsUser() {
//		UserResponseDTO user = createUserResponse();
//		ResponseEntity<UserResponseDTO> response = userController.find(1L);
//
//		assertThat(response).isNotNull();
//		assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo(user.getName());
//		assertThat(response.getStatusCodeValue()).isEqualTo(200);
//	}
//
//	@Test
//	void givenFind_WhenUserWithInformedEmailExists_ThenReturnsUser() {
//		UserResponseDTO user = createUserResponse();
//		ResponseEntity<UserResponseDTO> response = userController.find("xxx");
//
//		assertThat(response).isNotNull();
//		assertThat(Objects.requireNonNull(response.getBody()).getEmail()).isEqualTo(user.getEmail());
//		assertThat(response.getStatusCodeValue()).isEqualTo(200);
//	}
//
//	@Test
//	void givenCreate_WhenTheDataSentIsCorrect_ThenSavesUser() {
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//		ResponseEntity<UserResponseDTO> response = userController.create(createUserToBeSaved());
//
//		assertThat(response).isNotNull();
//		assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo(createUserToBeSaved().getName());
//		assertThat(response.getStatusCodeValue()).isEqualTo(201);
//		assertThat(Objects.requireNonNull(response.getHeaders().getLocation()).getPath()).isEqualTo("/1");
//	}
//
//	@Test
//	void givenUpdate_WhenUserExistsAndDataSentIsCorrect_ThenUpdatesUser() {
//		ResponseEntity<UserResponseDTO> response = userController.update(createUserToUpdate());
//
//		assertThat(response).isNotNull();
//		assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(createUserToUpdate().getId());
//		assertThat(response.getStatusCodeValue()).isEqualTo(200);
//	}
//
//	@Test
//	void givenDelete_WhenUserExists_ThenRemovesUser() {
//		ResponseEntity<Void> response = userController.delete(1L);
//
//		assertThat(response).isNotNull();
//		assertThat(response.getStatusCodeValue()).isEqualTo(204);
//	}
//
//	@Test
//	void givenBeanValidation_WhenTheFieldsAreWrong_ThenReturnsErrors() {
//		Set<ConstraintViolation<UserDTO>> violations = validator.validate(createUserToError());
//
//		assertThat(violations).isNotEmpty();
//		assertThat(violations.size()).isEqualTo(3);
//	}
//}
