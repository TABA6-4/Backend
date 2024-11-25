package com.example.focus.service;

import com.example.focus.dto.user.UserDTO;
import com.example.focus.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validateEmail(String email) {
        // 이메일 유효성 검사 로직
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already taken.");
        }
    }

    public void validateName(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Name is already taken.");
        }
    }

    public void validatePassword(String password) {
        // 비밀번호 유효성 검사 로직
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (password.length() < 6 || password.length() > 100) {
            throw new IllegalArgumentException("Password must be between 6 and 100 characters.");
        }
        // 비밀번호 복잡도 검사 추가 가능 (예: 대문자, 특수 문자 포함 여부 등)
    }

    public void validateUserDTO(UserDTO userDTO) {
        // UserDTO 전체 유효성 검사 (이메일, 이름, 비밀번호)
        validateEmail(userDTO.getEmail());
        validateName(userDTO.getUsername());
        validatePassword(userDTO.getPassword());
    }

}