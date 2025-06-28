package com.example.demo.dto;

import java.util.List;

public record UserResponse(
    Integer id,
    String email,
    String handle,
    String profileName,
    List<String> roles
) {}
