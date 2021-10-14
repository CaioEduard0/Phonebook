package com.example.phonebook.services;

import com.example.phonebook.dto.UserDTO;
import com.example.phonebook.dto.UserResponseDTO;
import com.example.phonebook.dto.UserUpdateDTO;
import com.example.phonebook.entities.User;
import com.example.phonebook.mappers.UserMapper;
import com.example.phonebook.repositories.UserRepository;
import com.example.phonebook.services.exceptions.EmailNotFoundException;
import com.example.phonebook.services.exceptions.RepeatedEmailException;
import com.example.phonebook.services.exceptions.UserNotFoundException;
import com.example.phonebook.services.exceptions.UserWithContactsException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserResponseDTO find(Long id) {
		Optional<User> user = userRepository.findById(id);
		return userMapper.toDTO(user. orElseThrow(() -> new UserNotFoundException(id)));
	}
	
	public UserResponseDTO find(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return userMapper.toDTO(user. orElseThrow(() -> new EmailNotFoundException(email)));
	}
	
	public UserResponseDTO create(UserDTO userDTO) {
		User user = userMapper.toEntity(userDTO);
		Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
		if (userOptional.isPresent()) {
			throw new RepeatedEmailException(user.getEmail());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userMapper.toDTO(userRepository.save(user));
	}

	public UserResponseDTO update(UserUpdateDTO userUpdateDTO) {
		find(userUpdateDTO.getId());
		User user = userRepository.save(userMapper.toEntity(userUpdateDTO));
		return userMapper.toDTO(user);
	}
	
	public void delete(Long id) {
		find(id);
		if (userRepository.findById(id).get().getAgenda().isEmpty()) {
			userRepository.deleteById(id);
		} else {
			throw new UserWithContactsException();
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return user.orElseThrow(() -> new UsernameNotFoundException("User with " + email + " not found."));
	}
}
