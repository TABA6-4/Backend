package com.example.focus.repository;

import com.example.focus.entity.WeeklyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface WeeklyReportRepository extends JpaRepository<WeeklyReport, Long> {
    @Query("""
    SELECT new map(
        dr.date AS date,
        dr.totalTime AS total_time,
        cr.focusedTime AS focused_time,
        cr.notFocusedTime AS not_focused_time,
        cr.averageScore AS focus_rate
    )
    FROM DailyReport dr
    JOIN VideoSession vs ON dr.user.user_id = vs.user.user_id
    JOIN ConcentrationResult cr ON vs.session_id = cr.videoSession.session_id
    WHERE dr.user.user_id = :user_id
    AND dr.date BETWEEN :startDate AND :endDate
    ORDER BY dr.date
""")
    List<Map<String, Object>> findWeeklyReport(
            @Param("user_id") Long user_id,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
