package com.example.focus.controller;

import com.example.focus.dto.videoFrame.VideoFrameRequestDTO;
import com.example.focus.service.VideoFrameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/video-frame")
@RequiredArgsConstructor
public class VideoFrameController {

    private final VideoFrameService videoFrameService;

    @PostMapping
    public ResponseEntity<String> saveVideoFrame(@Valid @RequestBody VideoFrameRequestDTO request) {
        videoFrameService.saveVideoFrame(request);
        return ResponseEntity.ok("VideoFrame saved successfully");
    }
}