package com.example.focus.dto.videoSession;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoSessionImageDTO {
    private Long user_id;
    private String title; // 세션 제목
    private String image;
}
