package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.model.entity.User;

public interface UserRepository extends BaseRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    // This interface will automatically provide CRUD operations for the User entity
    // You can add custom query methods here if needed

    Optional<User> findByHandle(String handle);

    boolean existsByHandle(String handle);

}
