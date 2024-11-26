package com.example.focus.opencv;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
public class WebCamService {

    static {
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME); // OpenCV 네이티브 라이브러리 로드
    }

    public String captureImage() {
        VideoCapture camera = new VideoCapture(0); // 웹캠 연결 (0: 기본 웹캠)

        if (!camera.isOpened()) {
            return "Error: Cannot open webcam.";
        }

        Mat frame = new Mat();
        if (camera.read(frame)) {
            String outputFilePath = Paths.get("captured_image.jpg").toAbsolutePath().toString();
            Imgcodecs.imwrite(outputFilePath, frame); // 이미지 저장
            camera.release();
            return "Image saved successfully at: " + outputFilePath;
        } else {
            camera.release();
            return "Error: Could not read frame from webcam.";
        }
    }
}
