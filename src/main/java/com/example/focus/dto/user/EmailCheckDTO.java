package com.example.focus.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
public class EmailCheckDTO {

    @NotBlank(message = "Email cannot be empty.")
    private String email;

    public EmailCheckDTO(String email) {
        //@AllArgsConstructor 사용 안하고 생성자 명시함
        this.email = email;
    }
}
