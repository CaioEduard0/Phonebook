//package com.example.phonebook.test.services;
//
//import static com.example.phonebook.test.utils.ContactCreator.createContact;
//import static com.example.phonebook.test.utils.ContactCreator.createContactToBeSaved;
//import static com.example.phonebook.test.utils.ContactCreator.createContactToUpdate;
//import static com.example.phonebook.test.utils.ContactCreator.createValidContact;
//import static com.example.phonebook.test.utils.UserCreator.createUser;
//import static com.example.phonebook.test.utils.UserCreator.createValidUser;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatCode;
//import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.example.phonebook.entities.Contact;
//import com.example.phonebook.entities.User;
//import com.example.phonebook.repositories.ContactRepository;
//import com.example.phonebook.repositories.UserRepository;
//import com.example.phonebook.services.ContactService;
//import com.example.phonebook.services.exceptions.ContactNotFoundException;
//import com.example.phonebook.services.exceptions.UserNotFoundException;
//
//@ActiveProfiles("test")
//@ExtendWith(SpringExtension.class)
//class ContactServiceTest {
//
//	@InjectMocks
//	private ContactService contactService;
//
//	@Mock
//	private UserRepository userRepositoryMock;
//
//	@Mock
//	private ContactRepository contactRepositoryMock;
//
//	@BeforeEach
//	void setUp() {
//
//		when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(createValidUser()));
//
//		when(contactRepositoryMock.save(any(Contact.class))).thenReturn(createValidContact());
//
//		doNothing().when(contactRepositoryMock).delete(any(Contact.class));
//	}
//
//	@Test
//	void findAllContacts_ReturnsListOfContacts_WhenSuccessful() {
//		List<Contact> contacts = contactService.findAllContacts(1L);
//
//		assertThat(contacts).isNotEmpty().isNotNull().hasSize(1);
//	}
//
//	@Test
//	void findAllContacts_ReturnsEmptyList_WhenUserDoNotHaveContacts() {
//		when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(createUser()));
//		List<Contact> contacts = contactService.findAllContacts(1L);
//
//		assertThat(contacts).isEmpty();
//	}
//
//	@Test
//	void findContactsByName_ReturnsListOfContacts_WhenSuccessful() {
//		User user = createUser();
//		Contact contact = createContact();
//		contact.setUser(user);
//		when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(user));
//		when(contactRepositoryMock.findByName(anyString())).thenReturn(Arrays.asList(contact));
//		List<Contact> contacts = contactService.findContactsByName(1L, "José");
//
//		assertThat(contacts).isNotEmpty().isNotNull().hasSize(1);
//	}
//
//	@Test
//	void insertContact_SavesContact_WhenSuccessful() {
//		Contact contact = contactService.insertContact(1L, createContactToBeSaved());
//
//		assertThat(contact).isNotNull();
//		assertThat(contact.getName()).isEqualTo(createContactToBeSaved().getName());
//	}
//
//	@Test
//	void updateContact_UpdatesContact_WhenSuccessful() {
////		when(contactRepositoryMock.save(any(Contact.class))).thenReturn(createContactToUpdate());
////		Contact contact = contactService.insertContact(1L, createContactToBeSaved());
////		contactService.updateContact(1L, 1L, createContactToUpdate().contactToDto(createContactToUpdate()));
////
////		assertThat(contact).isNotNull();
////		assertThat(contact.getName()).isEqualTo(createContactToUpdate().getName());
//	}
//
//	@Test
//	void deleteContact_RemovesContact_WhenSuccessful() {
//		assertThatCode(() -> contactService.deleteContact(1L, 1L)).isNull();
//		assertThatCode(() -> contactService.deleteContact(1L, 1L)).doesNotThrowAnyException();
//	}
//
//	@Test
//	void findUser_ThrowsUserNotFoundException_WhenUserIsNotFound() {
//		when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
//
//		assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> contactService.deleteContact(1L, 1L));
//	}
//
//	@Test
//	void findContact_ThrowsContactNotFoundException_WhenContactIsNotFound() {
//		when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(createUser()));
//		assertThatExceptionOfType(ContactNotFoundException.class).isThrownBy(() -> contactService.deleteContact(1L, 1L));
//	}
//}
