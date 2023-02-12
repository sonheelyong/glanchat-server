package com.green.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String token;
    private String email;
    private String username;
    private String password;
    // private String id;
    private String phonenumber;
    private String local;
    private String role;
    private String intro;
    private String leavecheck;
    private String userfileid;
}
