package com.example.focus.repository;


import com.example.focus.entity.Planner;
import com.example.focus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Long> {

    // 유저와 deadline 기준으로 Planner 조회
    @Query("SELECT p FROM Planner p WHERE p.user = :user AND p.date = :date")
    List<Planner> findPlannersByUserAndDeadline(
            @Param("user") User user,
            @Param("date") LocalDate date
    );

    // 유저 기준으로 모든 Planner 조회
    @Query("SELECT p FROM Planner p WHERE p.user = :user")
    List<Planner> findPlannersByUser(
            @Param("user") User user
    );

}
