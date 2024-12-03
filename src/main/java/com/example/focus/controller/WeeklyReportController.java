package com.example.focus.controller;

import com.example.focus.dto.concentrationResult.ConcentrationSummaryDTO;
import com.example.focus.service.VideoSessionService;
import com.example.focus.service.WeeklyReportService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/weekly-report")
public class WeeklyReportController {

    private final WeeklyReportService weeklyReportService;
    private final VideoSessionService videoSessionService;


    @GetMapping("/{user_id}/{start_date}/summary")
    public ResponseEntity<List<ConcentrationSummaryDTO>> getWeeklyConcentrationSummary(
            @PathVariable Long user_id,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start_date
    ) {
        List<ConcentrationSummaryDTO> summaries = videoSessionService.getWeeklyConcentrationSummary(user_id, start_date);
        return ResponseEntity.ok(summaries);
    }
}
