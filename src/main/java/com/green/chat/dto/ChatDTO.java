package com.green.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatDTO {

    private String roomid;
    private String user1;
    private String user2;
    private String msg;
    private String status;
    
}
