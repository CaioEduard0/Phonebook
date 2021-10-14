package com.example.phonebook.test.services;

import static com.example.phonebook.test.utils.UserCreator.createUser;
import static com.example.phonebook.test.utils.UserCreator.createUserToUpdate;
import static com.example.phonebook.test.utils.UserCreator.createValidUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.phonebook.entities.User;
import com.example.phonebook.repositories.UserRepository;
import com.example.phonebook.services.UserService;
import com.example.phonebook.services.exceptions.EmailNotFoundException;
import com.example.phonebook.services.exceptions.RepeatedEmailException;
import com.example.phonebook.services.exceptions.UserNotFoundException;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepositoryMock;
	
	@BeforeEach
	void setUp() {
		
		when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(createValidUser()));
		
		when(userRepositoryMock.find(anyString())).thenReturn(Optional.of(createValidUser()));
		
		when(userRepositoryMock.save(any(User.class))).thenReturn(createValidUser());
		
		doNothing().when(userRepositoryMock).deleteById(anyLong());
	}
	
	@Test
	void findUserById_ReturnsUser_WhenSuccessful() {
		User userTest = userService.find(1L);
		
		assertThat(userTest).isNotNull().isInstanceOf(User.class);
		assertThat(userTest.getName()).isEqualTo(createUser().getName());
	}
	
	@Test
	void findUserById_ThrowsUserNotFoundException_WhenUserIsNotFound() {
		when(userRepositoryMock.findById(1L)).thenReturn(Optional.empty());
		
		assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> userService.find(1L));
	}
	
	@Test
	void findUserByEmail_ReturnsUser_WhenSuccessful() {
		Optional<User> userTest = userRepositoryMock.find("jose@gmail.com");
		
		assertThat(userTest).isNotEmpty().isNotNull();
		assertThat(userTest.get().getEmail()).isEqualTo(createUser().getEmail());
	}
	
	@Test
	void findUserByEmail_ThrowsEmailNotFoundException_WhenEmailIsNotFound() {
		when(userRepositoryMock.find("jose@gmail.com")).thenReturn(Optional.empty());
		
		assertThatExceptionOfType(EmailNotFoundException.class).isThrownBy(() -> userService.find("jose@gmail.com"));
	}
	
	@Test
	void insertUser_SavesUser_WhenSuccessful() {
		when(userRepositoryMock.find(anyString())).thenReturn(Optional.empty());
		User userSaved = userService.create(createUser());
		
		assertThat(userSaved).isNotNull().isInstanceOf(User.class);
		assertThat(userSaved.getName()).isEqualTo(createUser().getName());
	}
	
	@Test
	void insertUser_ThrowsRepeatedEmailException_WhenEmailIsAlreadyRegistered() {		
		assertThatExceptionOfType(RepeatedEmailException.class).isThrownBy(() -> userService.create(createUser()));
	}
	
	@Test
	void updateUser_UpdatesUser_WhenSuccessful() {
		when(userRepositoryMock.find(anyString())).thenReturn(Optional.empty());
		when(userRepositoryMock.save(any(User.class))).thenReturn(createUserToUpdate().DtoToUser(createUserToUpdate()));
		User user = userService.create(createUser());
		userService.update(1L, createUserToUpdate().DtoToUser(createUserToUpdate()));
		
		assertThat(user).isNotNull().isInstanceOf(User.class);
		assertThat(user.getName()).isEqualTo(createUserToUpdate().getName());
	}
	
	@Test
	void deleteUser_RemovesUser_WhenSuccessful() {
		when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(createUser()));
		
		assertThatCode(() -> userService.delete(1L)).doesNotThrowAnyException();
		assertThatCode(() -> userService.delete(1L)).isNull();
	}
}
