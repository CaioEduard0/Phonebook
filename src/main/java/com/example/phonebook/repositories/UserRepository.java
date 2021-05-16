package com.example.phonebook.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.phonebook.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByUsername(String username);
}