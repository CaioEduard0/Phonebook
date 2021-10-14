package com.example.phonebook.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.phonebook.entities.User;
import com.example.phonebook.repositories.UserRepository;
import com.example.phonebook.services.exceptions.EmailNotFoundException;
import com.example.phonebook.services.exceptions.RepeatedEmailException;
import com.example.phonebook.services.exceptions.RepeatedUsernameException;
import com.example.phonebook.services.exceptions.UserNotFoundException;
import com.example.phonebook.services.exceptions.UserWithContactsException;

@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User findUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new UserNotFoundException(id));
	}
	
	public User findUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return user.orElseThrow(() -> new EmailNotFoundException(email));
	}
	
	public User insertUser(User user) {
		Optional<User> email = userRepository.findByEmail(user.getEmail());
		Optional<User> username = userRepository.findByUsername(user.getUsername());
		if (email.isPresent()) {
			throw new RepeatedEmailException(user.getEmail());
		}		
		if (username.isPresent()) {
			throw new RepeatedUsernameException(user.getUsername());
		}		
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public void updateUser(Long id, User user) {
		User updatedUser = findUserById(id);
		updatedUser.setName(user.getName());
		updatedUser.setEmail(user.getEmail());
		userRepository.save(updatedUser);
	}
	
	public void deleteUser(Long id) {
		User user = findUserById(id);
		if (user.getAgenda().isEmpty()) {
			userRepository.deleteById(id);
		} else {
			throw new UserWithContactsException();
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		return user.orElseThrow(() -> new UsernameNotFoundException("User with " + username + " not found!"));
	}
}
