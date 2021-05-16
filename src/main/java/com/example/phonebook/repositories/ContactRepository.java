package com.example.phonebook.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.phonebook.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	public List<Contact> findByName(String name);
}