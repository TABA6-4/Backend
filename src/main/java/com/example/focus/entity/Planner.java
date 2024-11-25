package com.example.focus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "planner")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Planner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planner_id;

    private String title;
    private LocalDate date;
    private String state;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private VideoSession videoSession;


    @Builder
    public Planner(String title, LocalDate date, User user) {
        this.title = title;
        this.date = date;
        this.user = user;

    }



}
