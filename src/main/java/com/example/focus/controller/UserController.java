package com.example.focus.controller;

import com.example.focus.dto.user.EmailCheckDTO;
import com.example.focus.dto.user.NameCheckDTO;
import com.example.focus.dto.user.UserDTO;
import com.example.focus.entity.User;
import com.example.focus.repository.UserRepository;
import com.example.focus.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/check-email")
    public ResponseEntity<EmailCheckDTO> checkEmail(@Valid @RequestBody EmailCheckDTO emailCheckDTO) {
        userService.validateEmail(emailCheckDTO.getEmail());
        return ResponseEntity.ok(emailCheckDTO);
    }

    @PostMapping("/check-name")
    public ResponseEntity<NameCheckDTO> checkName(@Valid @RequestBody NameCheckDTO nameCheckDTO) {
        userService.validateName(nameCheckDTO.getUsername());
        return ResponseEntity.ok(nameCheckDTO);
    }

}
