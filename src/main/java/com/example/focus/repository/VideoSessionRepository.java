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

    @Query("SELECT v FROM VideoSession v WHERE v.user.user_id = :userId AND v.date = :date")
    List<VideoSession> findByUserAndDate(
            @Param("user") long userId,
            @Param("date") LocalDate date
    );

    @Query("SELECT vs FROM VideoSession vs " +
            "LEFT JOIN FETCH vs.concentrationResult cr " +
            "WHERE vs.user.user_id = :userId AND vs.date = :date")
    List<VideoSession> findAllByUserAndDateWithConcentrationResult(
            //집중도 결과와 함께 조회
            @Param("userId") Long userId,
            @Param("date") LocalDate date
    );

    @Query("SELECT v FROM VideoSession v WHERE v.user = :user AND v.title = :title AND v.endTime IS NULL")
    VideoSession findByUserAndTitle(
            @Param("user") User user,
            @Param("title") String title
    );
}// t
