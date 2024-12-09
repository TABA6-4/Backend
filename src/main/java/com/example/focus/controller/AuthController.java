package com.example.focus.controller;

import com.example.focus.dto.sign.SignInDto;
import com.example.focus.dto.sign.SignUpDto;
import com.example.focus.dto.sign.UserDto;
import com.example.focus.dto.jwt.JwtToken;
import com.example.focus.service.AuthService;
import com.example.focus.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@Tag(name = "user sign-up, sing-in", description = "API") // API를 그룹화할 태그명을 지정
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    @Operation(summary = "유저 회원가입", description = "회원가입 할 때 사용하는 API")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
        log.info(signUpDto.toString());
        UserDto savedMemberDto = authService.signUp(signUpDto);
        log.info(savedMemberDto.toString());
        return ResponseEntity.ok(savedMemberDto);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "유저 로그인", description = "로그인 할 때 사용하는 API, JWT 사용")
    public JwtToken signIn(@RequestBody SignInDto signInDto) {
        String email = signInDto.getEmail();
        String password = signInDto.getPassword();
        JwtToken jwtToken = authService.signIn(email, password);
        log.info("request user email = {}, password = {}", email, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/test")
    @Operation(summary = "로그인 확인", description = "jwt 토큰으로 로그인이 유지중인지 조회하는 API")
    public String test() {
        return SecurityUtil.getCurrentUserEmail();
    }

}