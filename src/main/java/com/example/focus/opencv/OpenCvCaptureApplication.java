package com.example.focus.opencv;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class OpenCvCaptureApplication {

    public static void main(String[] args) {
//        // OpenCV 네이티브 라이브러리 로드
//        // OpenCV의 함수 및 클래스 사용을 위해 라이브러리를 로드해야 함
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//
//        // JFrame을 생성하여 영상 출력을 위한 GUI 설정
//        JFrame jFrame = new JFrame("Webcam Viewer"); // JFrame 제목 설정
//        JLabel jLabel = new JLabel(); // 이미지를 표시할 JLabel 생성
//        jFrame.setContentPane(jLabel); // JFrame의 컨텐츠 패널로 JLabel 설정
//        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 동작 설정
//        jFrame.setVisible(true); // JFrame 보이기 설정
//        jFrame.setBounds(0, 0, 1280, 720); // JFrame 크기 설정
//
//        // VideoCapture 객체 생성
//        // 0은 기본 웹캠 장치 ID를 의미 (대부분의 노트북에서 기본값)
//        VideoCapture videoCapture = new VideoCapture(0);
//
//        // Mat 객체를 생성하여 프레임 데이터를 저장
//        Mat frame = new Mat();
//
//        // try-catch 블록으로 예외 처리
//        try {
//            // 무한 루프를 통해 웹캠에서 계속해서 프레임 읽기
//            while (videoCapture.read(frame)) { // read()는 프레임을 캡처하고 Mat에 저장. 성공 시 true 반환
//                // Mat 데이터를 BufferedImage로 변환하여 GUI에 표시
//                ImageIcon image = new ImageIcon(Mat2BufferedImage(frame)); // Mat을 BufferedImage로 변환 후 ImageIcon으로 래핑
//                jLabel.setIcon(image); // JLabel에 이미지 설정
//                jLabel.repaint(); // GUI를 다시 그려서 변경 사항 적용
//            }
//        } catch (Exception e) {
//            // 예외 발생 시 스택 트레이스 출력
//            e.printStackTrace();
//        } finally {
//            // 자원 해제를 위해 VideoCapture 객체를 닫음
//            if (videoCapture.isOpened()) {
//                videoCapture.release(); // 웹캠 리소스 해제
//            }
//        }
//    }
//
//    // Mat 객체를 BufferedImage로 변환하는 메서드
//    private static BufferedImage Mat2BufferedImage(Mat matrix) throws Exception {
//        // Mat 데이터를 byte 배열로 변환
//        MatOfByte matOfByte = new MatOfByte();
//        Imgcodecs.imencode(".jpg", matrix, matOfByte); // Mat 데이터를 JPEG 형식으로 인코딩
//        byte[] ba = matOfByte.toArray(); // MatOfByte를 byte 배열로 변환
//
//        // byte 배열을 BufferedImage로 변환
//        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
//        return bi; // 변환된 BufferedImage 반환
//    }

        SpringApplication.run(OpenCvCaptureApplication.class, args);
    }
}
