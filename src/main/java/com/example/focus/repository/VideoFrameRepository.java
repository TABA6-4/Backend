package com.example.focus.repository;

import com.example.focus.entity.VideoFrame;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VideoFrameRepository extends JpaRepository<VideoFrame, Long> {
}