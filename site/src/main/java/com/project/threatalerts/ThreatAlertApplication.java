package com.project.threatalerts;

import com.project.threatalerts.models.Role;
import com.project.threatalerts.models.User;
import com.project.threatalerts.repo.RoleRepository;
import com.project.threatalerts.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.util.Collections;

@SpringBootApplication
@EnableScheduling
public class ThreatAlertApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThreatAlertApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository,
								  PasswordEncoder passwordEncoder) {
		return args -> {
			Role adminRole = roleRepository.findByName("ROLE_ADMIN");
			if (adminRole == null) {
				adminRole = new Role();
				adminRole.setName("ROLE_ADMIN");
				roleRepository.save(adminRole);
			}
			if (!userRepository.findByUsername("nazar").isPresent()) {
				User admin = new User();
				admin.setUsername("nazar");
				admin.setPassword(passwordEncoder.encode("nazar"));
				admin.setRoles(Collections.singleton(adminRole));
				userRepository.save(admin);
			}
		};
	}
}
