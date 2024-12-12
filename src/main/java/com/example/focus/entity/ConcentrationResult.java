package com.example.focus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.ejb.Local;
import java.sql.Time;
import java.time.LocalTime;

@Entity
@Table(name = "concentration_result")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConcentrationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long result_id;

    private LocalTime focusedTime;
    private LocalTime notFocusedTime;
    private Double averageScore;

    @OneToOne
    @JoinColumn(name = "session_id", nullable = false)
    private VideoSession videoSession;
}
