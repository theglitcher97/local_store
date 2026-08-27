package com.store.local_store;

import com.store.local_store.persistence.entities.CategoryEntity;
import com.store.local_store.persistence.entities.ProductEntity;
import com.store.local_store.persistence.entities.UserEntity;
import com.store.local_store.persistence.repositories.CategoryEntityRepository;
import com.store.local_store.persistence.repositories.ProductEntityRepository;
import com.store.local_store.persistence.repositories.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class LocalStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalStoreApplication.class, args);
	}


	@Bean
	@jakarta.transaction.Transactional
	public CommandLineRunner createAdminUser(UserEntityRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
				UserEntity user = UserEntity.create("admin@gmail.com", passwordEncoder.encode("admin123"), "ROLE_ADMIN");
				userRepository.save(user);
				System.out.println("Admin user created!!");
			}

			if (userRepository.findByEmail("test_user").isEmpty()) {
				UserEntity user = UserEntity.create("test_user@gmail.com", passwordEncoder.encode("test123"), "ROLE_USER");
				userRepository.save(user);
				System.out.println("Test user created!!");
			}
		};
	}

	@Bean
	@Transactional
	public CommandLineRunner saveCategories(CategoryEntityRepository categoryRepository, ProductEntityRepository productRepository){
		return runner -> {
			CategoryEntity categoryEntity = new CategoryEntity(null, "technology");
			categoryRepository.save(categoryEntity);

			productRepository.saveAll(List.of(
                    new ProductEntity(null, "laptop", new BigDecimal("1500"), 100, categoryEntity),
                    new ProductEntity(null, "pants", new BigDecimal("50"), 100, categoryEntity),
                    new ProductEntity(null, "bed", new BigDecimal("250"), 100, categoryEntity)
            ));
		};
	}

}
