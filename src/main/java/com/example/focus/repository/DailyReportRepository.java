package com.example.focus.repository;


import com.example.focus.entity.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
    DailyReport findByDate(Date date);

    @Query("SELECT r FROM DailyReport r WHERE r.user.user_id = :userId AND r.date = :date")
    DailyReport findByUserIdAndDate(Long userId, Date date);
}
