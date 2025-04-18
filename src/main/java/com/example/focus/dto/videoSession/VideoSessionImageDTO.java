package com.example.focus.dto.videoSession;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoSessionImageDTO {
    private Long user_id;
    private String title; // 세션 제목
    //private byte[] image; // 바이너리 데이터를 저장하도록 수정
}
