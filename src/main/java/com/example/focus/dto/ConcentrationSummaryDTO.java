package com.example.focus.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ConcentrationSummaryDTO {
    private long totalFocusedTime;
    private long totalNotFocusedTime;
    private long totalDuration;
    private double focusedRatio;
    private double notFocusedRatio;

    public ConcentrationSummaryDTO(long totalFocusedTime, long totalNotFocusedTime, long totalDuration, double focusedRatio, double notFocusedRatio) {
        this.totalFocusedTime = totalFocusedTime;
        this.totalNotFocusedTime = totalNotFocusedTime;
        this.totalDuration = totalDuration;
        this.focusedRatio = focusedRatio;
        this.notFocusedRatio = notFocusedRatio;
    }

    // Getters and Setters
}
