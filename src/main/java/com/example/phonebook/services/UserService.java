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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User find(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new UserNotFoundException(id));
	}
	
	public User find(String email) {
		Optional<User> user = userRepository.find(email);
		return user.orElseThrow(() -> new EmailNotFoundException(email));
	}
	
	public User create(User user) {
		Optional<User> email = userRepository.find(user.getEmail());
		if (email.isPresent()) {
			throw new RepeatedEmailException(user.getEmail());
		}
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public void update(Long id, User user) {
		User updatedUser = find(id);
		updatedUser.setName(user.getName());
		updatedUser.setEmail(user.getEmail());
		userRepository.save(updatedUser);
	}
	
	public void delete(Long id) {
		User user = find(id);
		if (user.getAgenda().isEmpty()) {
			userRepository.deleteById(id);
		} else {
			throw new UserWithContactsException();
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		Optional<User> user = userRepository.find(email);
		return user.orElseThrow(() -> new UsernameNotFoundException("User with " + email + " not found!"));
	}
}
