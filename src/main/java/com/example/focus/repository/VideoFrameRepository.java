package com.example.focus.repository;

import com.example.focus.entity.VideoFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoFrameRepository extends JpaRepository<VideoFrame, Long> {

    @Query("""
    select v from VideoFrame v
    where v.videoSession.session_id = :sessionId
    """)
    List<VideoFrame> findAllVideoFrameBySessionId(@Param("sessionId") long sessionId);
}