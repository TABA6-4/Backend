package com.example.focus.service;

import com.example.focus.repository.WeeklyReportRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.time.LocalDate;

@Service
public class WeeklyReportService {

    private final WeeklyReportRepository weeklyReportRepository;

    public WeeklyReportService(WeeklyReportRepository weeklyReportRepository) {
        this.weeklyReportRepository = weeklyReportRepository;
    }


    public List<Map<String, Object>> getWeeklyReport(Long userId, LocalDate weekStartDate) {
        //주간 첫 날짜만 받아서 일주일 간의 리포트 출력
        LocalDate weekEndDate = weekStartDate.plusDays(6); // 주간 끝 날짜 계산
        return weeklyReportRepository.findWeeklyReport(userId, weekStartDate, weekEndDate);
    }
}
