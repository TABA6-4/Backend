package com.example.focus.dto.planner;


import com.example.focus.entity.Planner;
import com.example.focus.entity.User;
import lombok.*;

import java.time.LocalDate;

@Data
public class planRequestDTO {
    private Long user_id;
    private String title;
    private LocalDate date;


    public static Planner toPlanner(planRequestDTO dto, User user){
        Planner planner = new Planner().builder()
                .title(dto.getTitle())
                .date(dto.getDate())
                .user(user)
                .build();
        return planner;
    }
}
