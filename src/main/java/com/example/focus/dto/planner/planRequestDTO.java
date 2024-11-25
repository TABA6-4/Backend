package com.example.focus.dto.planner;


import com.example.focus.entity.Planner;
import com.example.focus.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
public class planRequestDTO {
    private String title;
    private LocalDate date;
    private long id;


    public static Planner toPlanner(planRequestDTO dto, User user){
        Planner planner = new Planner().builder()
                .title(dto.getTitle())
                .date(dto.getDate())
                .user(user)
                .build();
        return planner;
    }
}
