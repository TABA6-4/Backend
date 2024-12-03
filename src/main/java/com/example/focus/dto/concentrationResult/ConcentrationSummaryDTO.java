package com.example.focus.dto.concentrationResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
@AllArgsConstructor
public class ConcentrationSummaryDTO {
    private LocalDate date;           // 날짜
    private Long userId;              // 사용자 ID
    private long totalFocusedTime;    // 집중 시간 (초)
    private long totalNotFocusedTime; // 비집중 시간 (초)
    private long totalDuration;       // 총 세션 길이 (초)
    private double focusedRatio;      // 집중 비율 (0~1)
    private double notFocusedRatio;   // 비집중 비율 (0~1)
}