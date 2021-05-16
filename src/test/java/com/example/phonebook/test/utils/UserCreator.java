package com.example.phonebook.test.utils;

import com.example.phonebook.dto.UserCreatorDTO;
import com.example.phonebook.dto.UserDTO;
import com.example.phonebook.entities.User;

public class UserCreator {
	
	public static User createUser() {
		User user = new User();
		user.setName("José");
		user.setEmail("jose@gmail.com");
		user.setPassword("{bcrypt}$2a$10$h1UiVi/kvWUAr5E5MyVOqO4gljJ7f9./pkpPHEIvCkSnh6cyENvYK");
		user.setUsername("jose");
		user.setAuthorities("ROLE_USER,ROLE_ADMIN");
		return user;
	}
	
	public static User createValidUser() {
		User user = createUser();
		user.setId(1L);
		user.getAgenda().add(ContactCreator.createValidContact());
		return user;
	}
	
//	public static User createValidUserr() {
//		User user = createUser();
//		user.setId(1L);
//		user.getAgenda().add(ContactCreator.createContact());
//		return user;
//	}
	
	public static UserCreatorDTO createUserToBeSaved() {
		UserCreatorDTO user = new UserCreatorDTO();
		user.setName("Pedro");
		user.setEmail("pedro@gmail.com");
		user.setPassword("123456");
		user.setUsername("pedro");
		user.setAuthorities("ROLE_USER,ROLE_ADMIN");
		return user;
	}
	
	public static UserDTO createUserToUpdate() {
		UserDTO userDto = new UserDTO();
		userDto.setName("Marcos");
		userDto.setEmail("marcos@gmail.com");
		return userDto;
	}
	
	public static User createUserToBeDeleted() {
		User user = new User();
		user.setId(1L);
		user.setName("Pedro");
		user.setEmail("pedro@gmail.com");
		user.setPassword("{bcrypt}$2a$10$h1UiVi/kvWUAr5E5MyVOqO4gljJ7f9./pkpPHEIvCkSnh6cyENvYK");
		user.setUsername("pedro");
		user.setAuthorities("ROLE_USER");
		return user;
	}
	
	public static UserCreatorDTO createUserToError() {
		UserCreatorDTO user = new UserCreatorDTO();
		user.setName("J");
		user.setEmail("gmail.com");
		user.setPassword("12345");
		user.setUsername("J");
		user.setAuthorities("ROLE_USER,ROLE_ADMIN");
		return user;
	}
}
