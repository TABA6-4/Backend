package com.example.focus.repository;

import com.example.focus.entity.User;
import com.example.focus.entity.VideoSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VideoSessionRepository extends JpaRepository<VideoSession, Long> {

    @Query("SELECT v FROM VideoSession v WHERE v.user = :user AND v.date = :date")
    List<VideoSession> findVideoSessionsByUserAndDate(
            @Param("user") User user,
            @Param("date") LocalDate date
    );

}// t
