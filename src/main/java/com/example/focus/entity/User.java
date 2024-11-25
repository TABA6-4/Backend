package com.example.focus.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "passwd", nullable = false)
    private String passwd;

    @Column(name = "createAt")
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Planner> planners;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<VideoSession> videoSessions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DailyReport> dailyReports;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WeeklyReport> weeklyReports;

    public User(String name, String email, String passwd) {
        this.email = email;
        this.name = name;
        this.passwd = passwd;
        this.createAt = LocalDateTime.now();
    }
}