package com.example.focus.controller;

import com.example.focus.dto.concentrationResult.ConcentrationSummaryDTO;
import com.example.focus.service.VideoSessionService;
import com.example.focus.service.WeeklyReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/weekly-report")
@Tag(name="주간 리포트 조회", description = "API")
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
