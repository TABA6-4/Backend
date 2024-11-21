package com.example.focus.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NameCheckDTO {
    private String name;

    public NameCheckDTO(String name) {
        //@AllArgsConstructor 사용 안하고 생성자 명시함
        this.name = name;
    }
}
