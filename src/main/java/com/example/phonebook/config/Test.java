package com.example.phonebook.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.phonebook.entities.Contact;
import com.example.phonebook.entities.User;
import com.example.phonebook.repositories.ContactRepository;
import com.example.phonebook.repositories.UserRepository;

@Configuration
@Profile("test")
public class Test implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		User u1 = new User(null, "Maria", "maria@gmail.com", "12345");
		User u2 = new User(null, "Alex", "alex@gmail.com", "12345");
		User u3 = new User(null, "Bob", "bob@gmail.com", "12345");
		User u4 = new User(null, "Kevin", "kevin@gmail.com", "12345");
		User u5 = new User(null, "Ashley", "ashley@gmail.com", "12345");
	
		userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5));
		
		Contact c1 = new Contact(null, "John", "8888-8888", u1);
		Contact c2 = new Contact(null, "John", "8888-8888", u2);
		Contact c3 = new Contact(null, "Carrie", "8888-8888", u3);
		Contact c4 = new Contact(null, "Carrie", "8888-8888", u4);
		Contact c5 = new Contact(null, "Katie", "8888-8888", u5);
		Contact c6 = new Contact(null, "Katie", "8888-8888", u1);
		Contact c7 = new Contact(null, "Steve", "8888-8888", u2);
		Contact c8 = new Contact(null, "Steve", "8888-8888", u3);
		Contact c9 = new Contact(null, "Carl", "8888-8888", u4);
		Contact c10 = new Contact(null, "Carl", "8888-8888", u5);
		
		contactRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10));	
	}
}
