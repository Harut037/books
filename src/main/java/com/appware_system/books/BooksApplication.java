package com.appware_system.books;

import com.appware_system.books.model.entity.UserEntity;
import com.appware_system.books.model.enums.Role;
import com.appware_system.books.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class BooksApplication implements CommandLineRunner {

	private final UserRepository userRepository;



	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserEntity adminAccount = userRepository.findByRole(Role.ADMIN);
		if (adminAccount == null){
			UserEntity userEntity = new UserEntity();
			userEntity.setEmail("admin@gmail.com");
			userEntity.setFirstName("admin");
			userEntity.setLastName("admin");
			userEntity.setRole(Role.ADMIN);
			userEntity.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(userEntity);
		}
	}
}
