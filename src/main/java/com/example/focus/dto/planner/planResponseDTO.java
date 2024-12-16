package com.example.focus.dto.planner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class planResponseDTO {
    private Long plannerId;
    private String title;
    private LocalDate date;
    private String state;
}
