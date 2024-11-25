package com.example.focus.dto.user;

import com.example.focus.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
public class UserResponseDTO {
    private Long getUser_id;
    private String name;
    private String email;

    public UserResponseDTO(User user) {
        this.name = user.getName();
        this.getUser_id = user.getUser_id();
        this.email = user.getEmail();
    }
}
