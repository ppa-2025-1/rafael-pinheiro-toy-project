package com.example.demo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.model.entity.Role;
import com.example.demo.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class DemoApplication {

	private final RoleRepository roleRepository;

	DemoApplication(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	void criarRoles() {
		List.of("USER", "GUEST", "VIEWER").forEach(role -> {
			var r = new Role();
			r.setName("ROLE_" + role);
			r.setCreatedAt(LocalDateTime.now());
			r.setUpdatedAt(LocalDateTime.now());
			roleRepository.save(r);
		});
	}
}
