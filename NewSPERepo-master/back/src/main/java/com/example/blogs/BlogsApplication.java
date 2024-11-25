package com.example.blogs;

import com.example.blogs.config.AppConstants;
import com.example.blogs.entities.Role;
import com.example.blogs.entities.User;
import com.example.blogs.payloads.UserDto;
import com.example.blogs.services.UserService;
import com.example.blogs.services.CategoryService;  // Make sure this is correctly imported.
import com.example.blogs.payloads.CategoryDto;    // Make sure this is correctly imported.
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;

@SpringBootApplication
public class BlogsApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogsApplication.class, args);
	}

	@Component
	class AdminInitializer implements CommandLineRunner {
		@Autowired
		private UserService userService;
		@Autowired
		private CategoryService categoryService;
		@Autowired
		private ModelMapper modelMapper;

		@Transactional
		public void run(String... args) throws Exception {
			// Initialize admin user if not already present
			if (userService.getAdminList().isEmpty()) {
				User user = new User();
				user.setEmail("admin@gmail.com");
				user.setPassword("admin"); // Consider encrypting the password
				user.setRole(Role.ADMIN);  // Ensure you have the correct role set up
				user.setName("Admin");
				user.setAbout("I am ADMIN");
				userService.addUser(user);
			}

			// Initialize categories if not already present
			if (categoryService.getCategories().isEmpty()) {
				createCategory("All about technology", "Technology");
				createCategory("Self improvement and help", "Self Help");
				createCategory("News and updates about sports", "Sports");
				createCategory("Travel guides and experiences", "Travel");
				createCategory("Delicious food recipes and tips", "Food");
				createCategory("Fitness routines and health tips", "Fitness");
			}
		}

		private void createCategory(String description, String title) {
			CategoryDto categoryDto = new CategoryDto();
			categoryDto.setCategoryDescription(description);
			categoryDto.setCategoryTitle(title);
			categoryService.createCategory(categoryDto);
		}
	}
}
