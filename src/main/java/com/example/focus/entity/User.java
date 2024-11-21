package com.example.focus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "passwd", nullable = false)
    private String passwd;

    @Column(name = "state")
    private int state = 1;

    @Column(name = "createAt")
    private LocalDateTime createAt;

    @Column(name = "updateAt")
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Planner> planners;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MeasureResult> measureResults;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Measurement> measurements;

    public User(String email, String name, String passwd) {
        this.email = email;
        this.name = name;
        this.passwd = passwd;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }
}