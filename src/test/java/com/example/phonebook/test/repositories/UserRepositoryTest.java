//package com.example.phonebook.test.repositories;
//
//import static com.example.phonebook.test.utils.UserCreator.createUser;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import com.example.phonebook.entities.User;
//import com.example.phonebook.repositories.UserRepository;
//
//@ActiveProfiles("test")
//@DataJpaTest
//class UserRepositoryTest {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Test
//	void findById_ReturnsUser_WhenSuccessful() {
//		User user = userRepository.save(createUser());
//		Optional<User> userTest = userRepository.findById(user.getId());
//
//		assertThat(userTest).isNotEmpty().isNotNull();
//		assertThat(userTest.get().getId()).isEqualTo(user.getId());
//		assertThat(userTest.get().getName()).isEqualTo(user.getName());
//	}
//
//	@Test
//	void findByEmail_ReturnsUser_WhenSuccessful() {
//		User user = userRepository.save(createUser());
//		Optional<User> userTest = userRepository.findByEmail(user.getEmail());
//
//		assertThat(userTest).isNotEmpty().isNotNull();
//		assertThat(userTest.get()).isEqualTo(user);
//	}
//
//	@Test
//	void save_SaveAndReturnsUser_WhenSuccessful() {
//		User user = createUser();
//		User userSaved = userRepository.save(user);
//
//		assertThat(userSaved).isNotNull();
//		assertThat(userSaved.getId()).isNotNull();
//		assertThat(userSaved.getName()).isEqualTo(user.getName());
//	}
//
//	@Test
//	void save_UpdatesUser_WhenSuccessful() {
//		User user = userRepository.save(createUser());
//		user.setName("Marcos Leal");
//		user.setEmail("marcos@gmail.com");
//		User userUpdated = userRepository.save(user);
//
//		assertThat(userUpdated).isNotNull();
//		assertThat(userUpdated).isEqualTo(user);
//	}
//
//	@Test
//	void delete_RemovesUser_WhenSuccessful() {
//		User user = userRepository.save(createUser());
//		userRepository.delete(user);
//		Optional<User> deletedUser = userRepository.findById(user.getId());
//
//		assertThat(deletedUser).isEmpty();
//	}
//}
