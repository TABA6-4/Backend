package com.example.focus.dto.user;

import com.example.focus.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private String name;
    private String email;

    public UserResponseDTO(User user) {
        this.name = user.getName();
        this.userId = user.getUserId();
        this.email = user.getEmail();
    }
}
