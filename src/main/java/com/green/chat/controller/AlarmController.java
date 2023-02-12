package com.green.chat.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.chat.model.AlarmEntity;
import com.green.chat.service.AlarmService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/alarm")


public class AlarmController {

    @Autowired
    AlarmService alarmService;
    
    // 전체알람 끄기/켜기  0:켜짐, 1:꺼짐
    @PutMapping("/all")
    public void allAlarm(@AuthenticationPrincipal String user_email)
    {
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity = alarmService.findById(user_email);

        String allAlarm = alarmEntity.getAllAlarm();
        if(allAlarm.equals("0")){
            alarmEntity.setAllAlarm("1");
            alarmEntity.setChatAlarm("1");
            alarmEntity.setFollowerAlarm("1");
            alarmEntity.setReplyAlarm("1");
        }else{
            alarmEntity.setAllAlarm("0");
            alarmEntity.setChatAlarm("0");
            alarmEntity.setFollowerAlarm("0");
            alarmEntity.setReplyAlarm("0");
        }
        alarmService.save(alarmEntity);
    }
       
    // 채팅알림 끄기/켜기
    @PutMapping("/chat")
    public void chatAlarm(@AuthenticationPrincipal String user_email)
    {
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity = alarmService.findById(user_email);
        String chatAlarm = alarmEntity.getChatAlarm();
        if(chatAlarm.equals("0")){
            alarmEntity.setChatAlarm("1");
        }else{
            alarmEntity.setChatAlarm("0");
        }
        alarmService.save(alarmEntity);
    }
    
    // 친구추가 알림 끄기/켜기
    @PutMapping("/follow")
    public void followAlarm(@AuthenticationPrincipal String user_email)
    {
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity = alarmService.findById(user_email);
        String follwAlarm = alarmEntity.getFollowerAlarm();
        if(follwAlarm.equals("0")){
            alarmEntity.setFollowerAlarm("1");
        }else{
            alarmEntity.setFollowerAlarm("0");
        }
        alarmService.save(alarmEntity);
    } 

    // 댓글 알림 끄기/켜기
    @PutMapping("/reply")
    public void replyAlarm(@AuthenticationPrincipal String user_email)
    {
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity = alarmService.findById(user_email);
        String replyAlarm = alarmEntity.getReplyAlarm();
        if(replyAlarm.equals("0")){
            alarmEntity.setReplyAlarm("1");
        }else{
            alarmEntity.setReplyAlarm("0");
        }
        alarmService.save(alarmEntity);
    } 
    
    // 알림설정 보기
    @GetMapping
    public ResponseEntity<?> getAlarm(@AuthenticationPrincipal String user_email)
    {   
        Optional<AlarmEntity> alarm = alarmService.getAlarm(user_email);
        
        return ResponseEntity.ok(alarm);
    }

    
    
}
