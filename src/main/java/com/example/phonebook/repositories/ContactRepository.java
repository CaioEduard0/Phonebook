package com.example.phonebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.phonebook.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	
}