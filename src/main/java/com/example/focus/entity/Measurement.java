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
@Table(name = "Measurements")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measureId;

    private String intensity; // 진동도
    private LocalDateTime startTime; // 측정 시각

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToMany(mappedBy = "measurement", cascade = CascadeType.ALL)
    private List<MeasureResult> measureResults;

}
