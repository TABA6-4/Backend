package com.example.focus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

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

    private Time focusedTime;
    private Time notFocusedTime;
    private Double averageScore;

    @OneToOne
    @JoinColumn(name = "session_id", nullable = false)
    private VideoSession videoSession;


}
