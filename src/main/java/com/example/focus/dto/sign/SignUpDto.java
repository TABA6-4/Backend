package com.example.focus.dto.sign;

import com.example.focus.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {

    private String email;
    private String password;
    private String username;
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public User toEntity(String encodedPassword, List<String> roles) {

        return User.builder()
                .email(email)
                .password(encodedPassword)
                .username(username)
                .roles(roles)
                .build();
    }
}