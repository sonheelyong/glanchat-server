package com.green.chat.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageDTO {
//1111
    private String senderName;
    private String receiverName;
    private String message;
    private LocalDateTime date = LocalDateTime.now();
    private Status status;
}