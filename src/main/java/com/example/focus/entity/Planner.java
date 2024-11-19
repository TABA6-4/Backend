package com.example.focus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Planner")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Planner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlannerId")
    private Long PlannerId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "createAt")
    private LocalDateTime createAt;

    @Column(name = "updateAt")
    private LocalDateTime updateAt;

    @Column(name = "state")
    private int state = 1;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}
