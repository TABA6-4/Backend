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

    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (userRepository.existsByName(name)) {
            throw new IllegalArgumentException("Name is already taken.");
        }
    }

    public void validatePassword(String passwd) {
        // 비밀번호 유효성 검사 로직
        if (passwd == null || passwd.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (passwd.length() < 6 || passwd.length() > 100) {
            throw new IllegalArgumentException("Password must be between 6 and 100 characters.");
        }
        // 비밀번호 복잡도 검사 추가 가능 (예: 대문자, 특수 문자 포함 여부 등)
    }

    public void validateUserDTO(UserDTO userDTO) {
        // UserDTO 전체 유효성 검사 (이메일, 이름, 비밀번호)
        validateEmail(userDTO.getEmail());
        validateName(userDTO.getName());
        validatePassword(userDTO.getPasswd());
    }

}