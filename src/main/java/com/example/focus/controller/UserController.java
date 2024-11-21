package com.example.focus.controller;

import com.example.focus.dto.user.EmailCheckDTO;
import com.example.focus.dto.user.NameCheckDTO;
import com.example.focus.dto.user.UserRegistrationDTO;
import com.example.focus.entity.User;
import com.example.focus.repository.UserRepository;
import com.example.focus.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @GetMapping("/register")
    public String register() {
        return "Registration page or logic here";
    }

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserService userService;


    @PostMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestBody EmailCheckDTO emailCheckDTO) {
        userService.validateEmail(emailCheckDTO.getEmail());
        return ResponseEntity.ok("Email is valid and available.");
    }

    @PostMapping("/check-name")
    public ResponseEntity<String> checkName(@RequestBody NameCheckDTO nameCheckDTO) {
        userService.validateName(nameCheckDTO.getName());
        return ResponseEntity.ok("Name is valid and available.");
    }

    /*@PostMapping("/register")
    @Transactional
    public ResponseEntity<String> registerUser(@RequestBody User user) {

        if (user.getPasswd() == null || user.getPasswd().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be null or empty");
        }
        user.setCreateAt(LocalDateTime.now());  // 생성 시간 설정
        user.setUpdateAt(LocalDateTime.now());  // 업데이트 시간 설정
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }*/

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<String> registerUser(UserRegistrationDTO dto) {
        userService.validateEmail(dto.getEmail());
        userService.validateName(dto.getName());
        User user = new User(dto.getEmail(), dto.getName(), dto.getPassword());
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}
