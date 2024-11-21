package com.example.focus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Planner")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Planner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plannerId")
    private Long plannerId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "deadline", nullable = false)
    private Date deadline;

    @Column(name = "createAt")
    private LocalDateTime createAt;

    @Column(name = "updateAt")
    private LocalDateTime updateAt;

    @Column(name = "state")
    private int state = 1;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Builder
    public Planner(String content, Date deadline, User user) {
        this.content = content;
        this.deadline = deadline;
        this.user = user;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }



}
