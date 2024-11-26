package com.example.focus.dto.WeeklyReport;

import com.example.focus.entity.WeeklyReport;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class WeeklyReportDTO {
    private Long weeklyReport_id;
    private Long user_id;
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private Long totalFocusedTime;

    public WeeklyReportDTO(WeeklyReport weeklyReport) {
        this.weeklyReport_id = weeklyReport.getWeeklyReport_id();
        this.user_id = weeklyReport.getUser().getUser_id();
        this.weekStartDate = weeklyReport.getWeekStartDate();
        this.weekEndDate = weeklyReport.getWeekEndDate();
        this.totalFocusedTime = weeklyReport.getTotalFocusedTime();
    }
}
