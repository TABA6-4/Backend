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
@Table(name = "dailyReport")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class dailyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private LocalDateTime createAt;
    private int state;

    @ManyToOne
    @JoinColumn(name = "resultId", nullable = false)
    private MeasureResult measureResult;
}
