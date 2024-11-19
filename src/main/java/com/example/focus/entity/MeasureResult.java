package com.example.focus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "MeasureResults")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeasureResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private int state;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "measureId", nullable = false)
    private Measurement measurement;

    @OneToMany(mappedBy = "measureResult", cascade = CascadeType.ALL)
    private List<dailyReport> dailyReports;

    // Getters and Setters
}