package com.example.demo.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.NewUser;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.business.UserBusiness;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.validation.Valid;

// fat model
// fat controller (Anti-Pattern: o que não fazer)

// thin controller e um fat model
// controller deve ter apenas o essencial
// para lidar com a requisição
// as regras de negócio ficam no model (business, service, entidade)

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends AbstractController {

    private final UserRepository userRepository;
    private final UserBusiness userBusiness;

    public UserController(UserRepository userRepository,
            UserBusiness userBusiness) {
        this.userRepository = userRepository;
        this.userBusiness = userBusiness;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createNewUser(
            @Valid @RequestBody NewUser newUser) {

        userBusiness.criarUsuario(newUser);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> users = userRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getHandle(),
                        user.getProfile() != null ? user.getProfile().getName() : null,
                        user.getRoles().stream().map(role -> role.getName()).toList()))
                .toList();

        return ResponseEntity.ok(users);
    }
}
