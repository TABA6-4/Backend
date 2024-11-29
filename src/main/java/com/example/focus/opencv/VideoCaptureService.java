package com.example.focus.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class VideoCaptureService {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // OpenCV 네이티브 라이브러리 로드
    }

    public void captureFrames(String videoPath, String outputDir, int intervalInSeconds) {
        // 1. 저장 디렉토리 생성
        File directory = new File(outputDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 2. OpenCV VideoCapture 객체 초기화
        VideoCapture videoCapture = new VideoCapture(videoPath);

        if (!videoCapture.isOpened()) {
            throw new RuntimeException("Could not open video file: " + videoPath);
        }

        // 3. FPS 및 간격 계산
        double fps = videoCapture.get(5); // 동영상 FPS 가져오기
        int frameInterval = (int) (fps * intervalInSeconds); // 5초마다 프레임 캡처

        Mat frame = new Mat();
        int frameCount = 0;
        int savedFrameCount = 0;

        // 4. 비디오에서 프레임 읽기 및 저장
        while (videoCapture.read(frame)) {
            if (frameCount % frameInterval == 0) {
                String fileName = String.format("%s/frame_%05d.jpg", outputDir, savedFrameCount);
                Imgcodecs.imwrite(fileName, frame); // 프레임을 이미지로 저장
                savedFrameCount++;
            }
            frameCount++;
        }

        videoCapture.release(); // 비디오 캡처 종료
        System.out.println("Total frames saved: " + savedFrameCount);
    }
}
