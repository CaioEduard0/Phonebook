//package com.example.phonebook.test.services;
//
//import static com.example.phonebook.test.utils.UserCreator.createUser;
//import static com.example.phonebook.test.utils.UserCreator.createUserToBeSaved;
//import static com.example.phonebook.test.utils.UserCreator.createUserToUpdate;
//import static com.example.phonebook.test.utils.UserCreator.createValidUser;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatCode;
//import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//
//import com.example.phonebook.dto.UserResponseDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.example.phonebook.entities.User;
//import com.example.phonebook.repositories.UserRepository;
//import com.example.phonebook.services.UserService;
//import com.example.phonebook.services.exceptions.EmailNotFoundException;
//import com.example.phonebook.services.exceptions.RepeatedEmailException;
//import com.example.phonebook.services.exceptions.UserNotFoundException;
//
//@ActiveProfiles("test")
//@ExtendWith(SpringExtension.class)
//class UserServiceTest {
//
//	@InjectMocks
//	private UserService userService;
//
//	@Mock
//	private UserRepository userRepositoryMock;
//
//	@BeforeEach
//	void setUp() {
//
//		when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(createValidUser()));
//
//		when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.of(createValidUser()));
//
//		when(userRepositoryMock.save(any(User.class))).thenReturn(createValidUser());
//
//		doNothing().when(userRepositoryMock).deleteById(anyLong());
//	}
//
//	@Test
//	void givenFind_WhenUserExists_ThenReturnsUser() {
//		UserResponseDTO userTest = userService.find(1L);
//
//		assertThat(userTest).isNotNull().isInstanceOf(UserResponseDTO.class);
//		assertThat(userTest.getName()).isEqualTo(createUser().getName());
//	}
//
//	@Test
//	void givenFind_WhenUserNotExists_ThenThrowsException() {
//		when(userRepositoryMock.findById(1L)).thenReturn(Optional.empty());
//
//		assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> userService.find(1L));
//	}
//
//	@Test
//	void givenFind_WhenUserWithInformedEmailExists_ThenReturnsUser() {
//		UserResponseDTO userTest = userService.find("jose@gmail.com");
//
//		assertThat(userTest).isNotNull();
//		assertThat(userTest.getEmail()).isEqualTo(createUser().getEmail());
//	}
//
//	@Test
//	void givenFind_WhenUserWithInformedEmailNotExists_ThenThrowsException() {
//		when(userRepositoryMock.findByEmail("jose@gmail.com")).thenReturn(Optional.empty());
//
//		assertThatExceptionOfType(EmailNotFoundException.class).isThrownBy(() -> userService.find("jose@gmail.com"));
//	}
//
//	@Test
//	void givenCreate_WhenTheDataSentIsCorrect_ThenSavesUser() {
//		when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.empty());
//		UserResponseDTO userSaved = userService.create(createUserToBeSaved());
//
//		assertThat(userSaved).isNotNull().isInstanceOf(UserResponseDTO.class);
//		assertThat(userSaved.getName()).isEqualTo(createUserToBeSaved().getName());
//	}
//
//	@Test
//	void givenCreate_WhenEmailIsAlreadyRegistered_ThenThrowsException() {
//		assertThatExceptionOfType(RepeatedEmailException.class).isThrownBy(() -> userService.create(createUserToBeSaved()));
//	}
//
//	@Test
//	void givenUpdate_WhenUserExistsAndDataSentIsCorrect_ThenUpdatesUser() {
//		UserResponseDTO userUpdated = userService.update(createUserToUpdate());
//
//		assertThat(userUpdated).isNotNull().isInstanceOf(UserResponseDTO.class);
//		assertThat(userUpdated.getName()).isEqualTo(createUserToUpdate().getName());
//	}
//
//	@Test
//	void givenDelete_WhenUserExists_ThenRemovesUser() {
//		when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(createUser()));
//
//		assertThatCode(() -> userService.delete(1L)).doesNotThrowAnyException();
//		assertThatCode(() -> userService.delete(1L)).isNull();
//	}
//}
