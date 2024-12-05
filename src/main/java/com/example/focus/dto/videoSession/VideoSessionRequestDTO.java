package com.example.focus.dto.videoSession;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoSessionRequestDTO {

    @NotNull(message = "User ID must not be null")
    private Long user_id;

    private String title; // 세션 제목

}