package com.example.focus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "daily_report")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DailyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long report_id;

    private Double percentage;
    private LocalDate date;
    private Long totalTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
