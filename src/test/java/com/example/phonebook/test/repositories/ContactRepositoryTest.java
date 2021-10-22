//package com.example.phonebook.test.repositories;
//
//import static com.example.phonebook.test.utils.ContactCreator.createContact;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import com.example.phonebook.entities.Contact;
//import com.example.phonebook.repositories.ContactRepository;
//
//@ActiveProfiles("test")
//@DataJpaTest
//class ContactRepositoryTest {
//
//	@Autowired
//	private ContactRepository contactRepository;
//
//	@Test
//	void findAll_ReturnsAllContacts_WhenSuccessful() {
//		contactRepository.save(createContact());
//		List<Contact> contacts = contactRepository.findAll();
//
//		assertThat(contacts).isNotEmpty().isNotNull().hasSize(1);
//		assertThat(contacts.get(0).getName()).isEqualTo(createContact().getName());
//	}
//
//	@Test
//	void findByName_ReturnsListOfContacts_WhenSuccessful() {
//		contactRepository.save(createContact());
//		List<Contact> contacts = contactRepository.findByName("Mario");
//
//		assertThat(contacts).isNotEmpty().isNotNull().hasSize(1);
//		assertThat(contacts.get(0).getName()).isEqualTo(createContact().getName());
//	}
//
//	@Test
//	void save_SaveAndReturnsContact_WhenSuccessful() {
//		Contact contact = contactRepository.save(createContact());
//
//		assertThat(contact).isNotNull();
//		assertThat(contact.getName()).isEqualTo(createContact().getName());
//	}
//
//	@Test
//	void save_UpdatesContact_WhenSuccessful() {
//		Contact contact = contactRepository.save(createContact());
//		contact.setName("José");
//		contact.setPhone("5555-5555");
//		contactRepository.save(contact);
//
//		assertThat(contact).isNotNull();
//		assertThat(contact.getName()).isEqualTo("José");
//	}
//
//	@Test
//	void delete_RemovesContact_WhenSuccessful() {
//		Contact contact = contactRepository.save(createContact());
//		contactRepository.delete(contact);
//		Optional<Contact> deletedContact = contactRepository.findById(contact.getId());
//
//		assertThat(deletedContact).isEmpty();
//	}
//}
