package com.green.chat.controller;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.chat.dto.CheckDTO;
import com.green.chat.dto.ResponseDTO;
import com.green.chat.dto.UserDTO;
import com.green.chat.model.UserEntity;
import com.green.chat.service.UserService;



@RestController
@RequestMapping("/auth")
public class PhoneMessage {
     
    
     DefaultMessageService messageService;

    @Autowired
    private UserService userService;


    public PhoneMessage() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        messageService = NurigoApp.INSTANCE.initialize("NCSZOYPFOUVAKN60", "IFH9CNR2XAPHGIZCKUE5BPBPI6EJGCFR", "https://api.coolsms.co.kr");
    }
     
    String check;

    public String checkNumber(){
        int authNo = (int)(Math.random() * (99999 - 10000 + 1)) + 10000;
        String check = Integer.toString(authNo);
		System.out.println("인증번호"+ check);
        return check;
    }

    public void setcheckNumber(String check){
        this.check = check;
    }
    
    public static String cleanPhoneNumber(String phoneNumber) {
        String regex = "^\\+?\\d{0,2}\\s|\\s";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.replaceAll("");
    }

    // 비밀번호 찾기용
    @PostMapping("/send-one")
    public ResponseEntity<?> sendOne(@RequestBody UserDTO userDTO) {

        String phoneNumber = userDTO.getPhonenumber();
        String cleanedNumber = cleanPhoneNumber(phoneNumber);
       
        UserEntity user = userService.findByEmail(userDTO.getEmail());
        System.out.println("!!!!!!!!!!!!!!"+user);

        if(user == null){
            ResponseDTO responseDTO = ResponseDTO.builder()
            .error("잘못된 이메일")
            .build();
            System.out.println(responseDTO.getError());
            return ResponseEntity.badRequest().body(responseDTO);
        }else{
        if(!user.getPhonenumber().equals(userDTO.getPhonenumber())){
            ResponseDTO responseDTO = ResponseDTO.builder()
            .error("잘못된 핸드폰번호")
            .build();
            System.out.println("핸드폰잘못"); 
            return ResponseEntity.badRequest().body(responseDTO);
        }else{


        String check2 = checkNumber();
        setcheckNumber(check2);
        
        Message message = new Message();
        message.setFrom("01052522947");
        message.setTo(cleanedNumber);   
        message.setText("글램챗 인증번호 : [" + check2 + "]");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return ResponseEntity.ok().body(response);
        }
    }
    }
    

    // 회원가입용
    @PostMapping("/sendauth")
    public ResponseEntity<?> sendauth(@RequestBody UserDTO userDTO){
        
        String phoneNumber = userDTO.getPhonenumber();
        String cleanedNumber = cleanPhoneNumber(phoneNumber);
    
        String check2 = checkNumber();
        setcheckNumber(check2);
        
        Message message = new Message();
        message.setFrom("01052522947");
        message.setTo(cleanedNumber);   
        message.setText("글램챗 인증번호 : [" + check2 + "]");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return ResponseEntity.ok().body(response);

    }



    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody CheckDTO checkDTO) {
        String cknum = checkDTO.getCknum();
        // String check3 = this.check;
        String check3 = "9999";
        System.out.println(cknum);
        System.out.println(check3);
        if(cknum.equals(check3)){
            setcheckNumber("9999");
            return ResponseEntity.ok().body("0"); 
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder()
            .error("잘못된 인증번호")
            .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

   
}

