package com.green.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FriendListDTO {
    private String email;
    private String requireemail;
    private String requirecheck;
    private String requsername;
}
