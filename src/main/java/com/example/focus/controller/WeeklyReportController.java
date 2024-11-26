package com.example.focus.controller;

import com.example.focus.service.WeeklyReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/weekly-report")
public class WeeklyReportController {

    private final WeeklyReportService weeklyReportService;

    public WeeklyReportController(WeeklyReportService weeklyReportService) {
        this.weeklyReportService = weeklyReportService;
    }

    @GetMapping("/{user_id}/{start_date}/summary")
    public ResponseEntity<List<Map<String, Object>>> getWeeklyReport(
            @PathVariable Long user_id,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start_date
    ) {
        List<Map<String, Object>> weeklyReport = weeklyReportService.getWeeklyReport(user_id, start_date);
        return ResponseEntity.ok(weeklyReport);
    }
}
