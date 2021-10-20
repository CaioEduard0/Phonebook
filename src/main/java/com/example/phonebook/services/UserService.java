package com.example.phonebook.services;

import com.example.phonebook.dto.UserDTO;
import com.example.phonebook.dto.UserResponseDTO;
import com.example.phonebook.dto.UserUpdateDTO;
import com.example.phonebook.entities.User;
import com.example.phonebook.exceptions.BadRequestException;
import com.example.phonebook.exceptions.NotFoundException;
import com.example.phonebook.mappers.UserMapper;
import com.example.phonebook.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
		return userMapper.toDTO(user. orElseThrow(NotFoundException::new));
	}
	
	public UserResponseDTO find(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return userMapper.toDTO(user. orElseThrow(NotFoundException::new));
	}
	
	public UserResponseDTO create(UserDTO userDTO) {
		User user = userMapper.toEntity(userDTO);
		Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
		if (userOptional.isPresent()) {
			throw new BadRequestException("The e-mail " + user.getEmail() + " is already registered.");
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
			throw new BadRequestException("The user has contacts and cannot be deleted.");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return user.orElseThrow(NotFoundException::new);
	}
}
