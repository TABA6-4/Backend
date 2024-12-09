package com.example.focus.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class FlaskService {

    private static final String FLASK_SERVER_URL = "http://43.203.180.244:5000/predict";
    private final RestTemplate restTemplate = new RestTemplate();

    public void sendToAIServer(byte[] image, Long session_id) {
        try {
            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // 멀티파트 요청 바디 구성
            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

            // 이미지 파일 추가
            Resource imageResource = new ByteArrayResource(image) {
                @Override
                public String getFilename() {
                    return "image.jpg"; // Flask에서 파일 이름이 필요할 경우
                }
            };
            requestBody.add("file", imageResource);

            // session_id 추가
            requestBody.add("session_id", session_id);

            // 요청 엔티티 생성
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Flask 서버로 요청 전송
            ResponseEntity<String> response = restTemplate.exchange(FLASK_SERVER_URL, HttpMethod.POST, requestEntity, String.class);

            // 응답 처리
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Response from AI server: " + response.getBody());
            } else {
                System.err.println("Failed to send data to AI server. Status Code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error occurred while sending data to AI server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
