package com.example.phonebook.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.phonebook.entities.Contact;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	List<Contact> findByName(String name);
}