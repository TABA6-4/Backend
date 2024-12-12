package com.example.focus.service;

import com.example.focus.dto.videoFrame.VideoFrameRequestDTO;
import com.example.focus.entity.VideoFrame;
import com.example.focus.entity.VideoSession;
import com.example.focus.repository.VideoFrameRepository;
import com.example.focus.repository.VideoSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VideoFrameService {

    @Autowired
    private VideoFrameRepository videoFrameRepository;

    @Autowired
    private VideoSessionRepository videoSessionRepository;

    public void saveVideoFrame(VideoFrameRequestDTO request) {
        // 세션 조회
        VideoSession session = videoSessionRepository.findById(request.getSession_id())
                .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + request.getSession_id()));

        // DTO를 사용하여 VideoFrame 생성
        VideoFrame videoFrame = request.toEntity(session);

        // 저장
        videoFrameRepository.save(videoFrame);
    }


}