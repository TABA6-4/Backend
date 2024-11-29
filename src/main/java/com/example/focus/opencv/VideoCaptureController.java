package com.example.focus.opencv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoCaptureController {

    @Autowired
    private VideoCaptureService videoCaptureService;

    @GetMapping("/capture-video")
    public String captureVideo(
            @RequestParam String videoPath,
            @RequestParam String outputDir
    ) {
        try {
            videoCaptureService.captureFrames(videoPath, outputDir, 5); // 5초마다 캡처
            return "Frames captured successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}