package com.green.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.chat.dto.PwChangeDTO;
import com.green.chat.dto.ResponseDTO;
import com.green.chat.dto.UserDTO;
import com.green.chat.model.UserEntity;
import com.green.chat.service.UserService;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserService userService;
    
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    

    // 비밀번호 변경
    @PutMapping("/changepw")
    public ResponseEntity<?> changepw(@AuthenticationPrincipal String user_email, @RequestBody PwChangeDTO pwChangeDTO ){
        // chkpw : 이전비밀번호(확인용), newpw(새 비밀번호)
       
        String chkpw = pwChangeDTO.getChkpw();
        String newpw = pwChangeDTO.getNewpw();

        String password = (passwordEncoder.encode(newpw));

        UserEntity user = userService.getByCredentials(
                        user_email,
                        chkpw,
                        passwordEncoder);
        
        if(user == null){
            ResponseDTO responseDTO = ResponseDTO.builder()
            .error("잘못된 비밀번호.")
            .build();
    return ResponseEntity
            .badRequest()
            .body(responseDTO); // 비밀번호 틀림
        }
       System.out.println("찾음"+user);
        user.setPassword(password);
        userService.save(user);
        return ResponseEntity.ok().body("0"); // 비밀번호 변경
        
    }
       
    // 자기소개 작성, 수정
    @PostMapping("/writeintro")
    public void writeInfo(@AuthenticationPrincipal String user_email, @RequestBody UserDTO userDTO){

        UserEntity user = userService.findByEmail(user_email);
        user.setIntro(userDTO.getIntro());
        userService.save(user);
    }

    // 자기소개 보기 (이메일로 유저찾기)
    @GetMapping("/getintro")
    public ResponseEntity<?> getIntro(@AuthenticationPrincipal String user_email){

        UserEntity user = userService.findByEmail(user_email);

        return ResponseEntity.ok(user);   // 프론트에서 intro만 받아쓸것.
    }

    @PutMapping("/updateinfo")
    public ResponseEntity<?> updateinfo(@AuthenticationPrincipal String user_email, @RequestBody UserDTO userDto){

        UserEntity user = userService.findByEmail(user_email);
        
        user.setUsername(userDto.getUsername());
        user.setIntro(userDto.getIntro());

        userService.save(user);

        return ResponseEntity.ok(user);
    }
     

   
}
