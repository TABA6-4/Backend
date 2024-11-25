package com.example.focus.controller;

import com.example.focus.dto.user.EmailCheckDTO;
import com.example.focus.dto.user.NameCheckDTO;
import com.example.focus.dto.user.UserDTO;
import com.example.focus.entity.User;
import com.example.focus.repository.UserRepository;
import com.example.focus.service.UserService;

import jakarta.validation.Valid;
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
    public ResponseEntity<EmailCheckDTO> checkEmail(@Valid @RequestBody EmailCheckDTO emailCheckDTO) {
        userService.validateEmail(emailCheckDTO.getEmail());
        return ResponseEntity.ok(emailCheckDTO);
    }

    @PostMapping("/check-name")
    public ResponseEntity<NameCheckDTO> checkName(@Valid @RequestBody NameCheckDTO nameCheckDTO) {
        userService.validateName(nameCheckDTO.getName());
        return ResponseEntity.ok(nameCheckDTO);
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        userService.validateEmail(userDTO.getEmail());
        userService.validateName(userDTO.getName());

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPasswd(userDTO.getPasswd());
        user.setCreateAt(LocalDateTime.now());
        userRepository.save(user);

        userDTO.setResponseMessage("User registered successfully");
        return ResponseEntity.ok(userDTO);
    }
}
