package com.example.focus.dto.user;

import com.example.focus.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
public class UserResponseDTO {
    private Long getUser_id;
    private String username;
    private String email;

    public UserResponseDTO(User user) {
        this.username = user.getUsername();
        this.getUser_id = user.getUser_id();
        this.email = user.getEmail();
    }
}
