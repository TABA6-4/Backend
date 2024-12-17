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

import org.springframework.beans.factory.annotation.Value;

@Service
public class FlaskService {

    @Value("${flask.server.url}")
    private String flaskServerUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public String sendToAIServer(byte[] image, Long session_id) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            Resource imageResource = new ByteArrayResource(image) {
                @Override
                public String getFilename() {
                    return "image.jpg";
                }
            };
            requestBody.add("file", imageResource);
            requestBody.add("session_id", session_id);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(flaskServerUrl, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Response from AI server: " + response.getBody());
                return response.getBody();
            } else {
                System.err.println("Failed to send data to AI server. Status Code: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error occurred while sending data to AI server: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
