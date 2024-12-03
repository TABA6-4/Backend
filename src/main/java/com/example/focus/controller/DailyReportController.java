package com.example.focus.controller;

import com.example.focus.dto.concentrationResult.ConcentrationSummaryDTO;
import com.example.focus.service.VideoSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/daily-report")
@RequiredArgsConstructor
public class DailyReportController {

    private final VideoSessionService videoSessionService;

    @GetMapping("/{user_id}/{date}/summary")
    //UserId, 날짜 받아서 일일 리포트 출력
    public ResponseEntity<ConcentrationSummaryDTO> getConcentrationSummary(
            @PathVariable Long user_id,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        ConcentrationSummaryDTO summary = videoSessionService.getConcentrationSummary(user_id, date);
        return ResponseEntity.ok(summary);
    }

}
