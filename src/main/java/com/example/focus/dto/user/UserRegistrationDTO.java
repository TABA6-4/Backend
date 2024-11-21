package com.example.focus.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationDTO {
    private String email;
    private String name;
    private String password;

    public UserRegistrationDTO(String email, String name, String password) {
        //@AllArgsConstructor 사용 안하고 생성자 명시함
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
