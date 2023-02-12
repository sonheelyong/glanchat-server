package com.green.chat.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import com.green.chat.dto.ChatDTO;
import com.green.chat.dto.Greeting;
import com.green.chat.dto.HelloMessage;
import com.green.chat.dto.MessageDTO;
import com.green.chat.model.MessageEntity;
import com.green.chat.dto.MessageDTO;
import com.green.chat.persistence.ChatRepository;
import com.green.chat.service.MessageService;

@Controller
public class ChatController {
    //111
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    private MessageService messageService;
   

    @MessageMapping("/message") // /app/message
    @SendTo("/chatroom/public")
    public MessageDTO receivePublicMessage(@Payload MessageDTO message, SimpMessageHeaderAccessor headerAccessor){

        String sessionId = headerAccessor.getSessionId();
        
        System.out.println("세션"+sessionId);
        System.out.println("전체:"+message);
        return message;
    }
    
    @MessageMapping("/private-message")
    private MessageDTO receivePrivateMessage(@Payload MessageDTO message){
        

        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message); // /user/david/private
        System.out.println("개인:"+message);
        
        MessageEntity entity = MessageEntity.builder()
                               .senderName(message.getSenderName())
                               .receiverName(message.getReceiverName())
                               .message(message.getMessage().toString())
                               .messagedate(message.getDate().toString())
                               .mesasagestatus(message.getStatus().toString())
                               .build();
        messageService.save(entity);                       
                            
       return message;
    }

    @MessageMapping("/hello")
   
    public Greeting greeting(HelloMessage message) throws Exception {
        System.out.println("으아아아아아ㅏ아앙"+message);

        simpMessagingTemplate.convertAndSendToUser(message.getReceivename(),"/alarm",message.getCont());
        Thread.sleep(1000); // simulated delay
        // return new Greeting("Hello, " + message.getName() + "!");
        return new Greeting("Hello, !");
    }

    @MessageMapping("/random")
    public ChatDTO RandomMessage( @Payload ChatDTO message){
        System.out.println("ㅇssss"+message.getRoomid());
        System.out.println("요기가메세지"+message);
        
        simpMessagingTemplate.convertAndSend("/sub/chat/"+message.getRoomid(),message);
        
        return message;
    }


}