package com.example.focus.repository;

import com.example.focus.entity.ConcentrationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConcentrationResultRepository extends JpaRepository<ConcentrationResult, Integer> {
    @Query("SELECT cr FROM ConcentrationResult cr WHERE cr.videoSession.session_id = :sessionId")
    ConcentrationResult findBySessionId(@Param("sessionId") long sessionId);
}
