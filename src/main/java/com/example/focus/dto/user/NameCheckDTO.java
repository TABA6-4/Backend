package com.example.focus.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
public class NameCheckDTO {

    @NotBlank(message = "Name cannot be empty.")
    private String name;

    public NameCheckDTO(String name) {
        //@AllArgsConstructor 사용 안하고 생성자 명시함
        this.name = name;
    }
}
