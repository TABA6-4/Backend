package com.example.focus.dto.planner;


import com.example.focus.entity.Planner;
import com.example.focus.entity.User;
import lombok.*;

import java.util.Date;

@Getter
public class planRequestDTO {
    private String content;
    private Date deadline;
    private long id;


    public static Planner toPlanner(planRequestDTO dto, User user){
        Planner planner = new Planner().builder()
                .content(dto.getContent())
                .deadline(dto.getDeadline())
                .user(user)
                .build();
        return planner;
    }
}
