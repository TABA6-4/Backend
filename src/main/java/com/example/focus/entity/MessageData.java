package com.example.focus.entity;

import lombok.Data;

@Data
public class MessageData {  //클라이언트로부터 받은 데이터 형식
    private Long user_id;
    private String title;
    private byte[] imageBytes;


}
