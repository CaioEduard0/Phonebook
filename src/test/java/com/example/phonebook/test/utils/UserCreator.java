//package com.example.phonebook.test.utils;
//
//import com.example.phonebook.dto.UserDTO;
//import com.example.phonebook.dto.UserResponseDTO;
//import com.example.phonebook.dto.UserUpdateDTO;
//import com.example.phonebook.entities.User;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Set;
//
//public class UserCreator {
//
//	public static User createUser() {
//		return User.builder()
//				.name("José")
//				.email("jose@gmail.com")
//				.password("{bcrypt}$2a$10$h1UiVi/kvWUAr5E5MyVOqO4gljJ7f9./pkpPHEIvCkSnh6cyENvYK")
//				.authorities(Set.of(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN")))
//				.build();
//
//	}
//
//	public static User createValidUser() {
//		User user = createUser();
//		user.setId(1L);
//		user.getAgenda().add(ContactCreator.createValidContact());
//		return user;
//	}
//
//	public static UserDTO createUserToBeSaved() {
//		return UserDTO.builder()
//				.name("Pedro")
//				.email("pedro@gmail.com")
//				.password("123456")
//				.authorities(Set.of(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN")))
//				.build();
//	}
//
//	public static UserUpdateDTO createUserToUpdate() {
//		return UserUpdateDTO.builder()
//				.id(1L)
//				.name("Marcos")
//				.email("marcos@gmail.com")
//				.build();
//	}
//
//	public static User createUserToBeDeleted() {
//		return User.builder()
//				.id(1L)
//				.name("Pedro")
//				.email("pedro@gmail.com")
//				.password("{bcrypt}$2a$10$h1UiVi/kvWUAr5E5MyVOqO4gljJ7f9./pkpPHEIvCkSnh6cyENvYK")
//				.authorities(Set.of(new SimpleGrantedAuthority("USER")))
//				.build();
//	}
//
//	public static UserDTO createUserToError() {
//		return UserDTO.builder()
//				.name("J")
//				.email("gmail.com")
//				.password("12345")
//				.authorities(Set.of(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN")))
//				.build();
//	}
//
//	public static UserResponseDTO createUserResponse() {
//		return UserResponseDTO.builder()
//				.id(1L)
//				.name("José")
//				.email("jose@gmail.com")
//				.authorities(Set.of(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN")))
//				.build();
//	}
//}
