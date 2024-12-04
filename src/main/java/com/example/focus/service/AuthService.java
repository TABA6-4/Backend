package com.example.focus.service;

import com.example.focus.dto.sign.SignUpDto;
import com.example.focus.dto.sign.UserDto;
import com.example.focus.dto.jwt.JwtToken;
import com.example.focus.jwt.JwtTokenProvider;
import com.example.focus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 처리
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return JwtToken
     */
    @Transactional
    public JwtToken signIn(String email, String password) {
        // 1. 이메일 + 비밀번호를 기반으로 Authentication 객체 생성 (아직 인증되지 않은 상태)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        // 2. AuthenticationManager를 통해 인증 (CustomUserDetailsService 호출)
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 성공 시 JWT 토큰 생성
        return jwtTokenProvider.generateToken(authentication);
    }

    /**
     * 회원가입 처리
     * @param signUpDto 회원가입 정보
     * @return UserDto
     */
    @Transactional
    public UserDto signUp(SignUpDto signUpDto) {
        // 이메일 중복 검사
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        // 사용자 이름 중복 검사
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }

        // 1. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        // 2. 기본 권한 설정
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER"); // ROLE 접두사를 포함해야 Spring Security와 호환

        // 3. 사용자 저장
        return UserDto.toDto(userRepository.save(signUpDto.toEntity(encodedPassword, roles)));
    }
}
