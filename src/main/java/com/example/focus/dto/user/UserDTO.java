package com.example.focus.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@Data
public class UserDTO {

    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Name cannot be empty.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
    private String name;

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
    private String passwd;

    private String responseMessage; // 응답용 메시지 필드 (예: "Email is valid")
}