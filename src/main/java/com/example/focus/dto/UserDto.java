package com.example.focus.dto;

import com.example.focus.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@Data
public class UserDto {
    private Long user_id;
    private String email;
    private String username;

    static public UserDto toDto(User user) {
        return UserDto.builder()
                .user_id(user.getUser_id())
                .username(user.getUsername())
                .email(user.getEmail()).build();
    }

    public User toEntity() {
        return User.builder()
                .user_id(user_id)
                .username(username)
                .email(email).build();
    }

}