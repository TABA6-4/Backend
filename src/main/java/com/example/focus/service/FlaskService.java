package com.example.focus.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

@Service
public class FlaskService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendToAIServer(String image, Long session_id) {
        try {
            String url = "http://localhost:5000/process-image";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("image", image);
            requestBody.put("session_id", session_id);

            String jsonBody = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            String response = restTemplate.postForObject(url, entity, String.class);
            System.out.println("Response from AI server: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
