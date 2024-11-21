package com.example.focus.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailCheckDTO {
    private String email;

    public EmailCheckDTO(String email) {
        //@AllArgsConstructor 사용 안하고 생성자 명시함
        this.email = email;
    }
}
