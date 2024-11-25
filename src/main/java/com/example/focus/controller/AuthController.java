package com.example.focus.controller;

import com.example.focus.dto.SignInDto;
import com.example.focus.dto.SignUpDto;
import com.example.focus.dto.UserDto;
import com.example.focus.dto.jwt.JwtToken;
import com.example.focus.service.AuthService;
import com.example.focus.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
        log.info(signUpDto.toString());
        UserDto savedMemberDto = authService.signUp(signUpDto);
        log.info(savedMemberDto.toString());
        return ResponseEntity.ok(savedMemberDto);
    }

        @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInDto signInDto) {
        String email = signInDto.getEmail();
        String password = signInDto.getPassword();
        JwtToken jwtToken = authService.signIn(email, password);
        log.info("request user email = {}, password = {}", email, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/test")
    public String test() {
        return SecurityUtil.getCurrentUserEmail();
    }

}