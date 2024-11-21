package com.example.focus.service;

import com.example.focus.dto.user.UserRegistrationDTO;
import com.example.focus.entity.User;
import com.example.focus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validateEmail(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use.");
        }
    }

    public void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (userRepository.existsByName(name)) {
            throw new IllegalArgumentException("Name already in use.");
        }
    }

    public void registerUser(UserRegistrationDTO dto) {
        validateEmail(dto.getEmail());
        validateName(dto.getName());
        User user = new User(dto.getEmail(), dto.getName(), dto.getPassword());
        userRepository.save(user);
    }

}