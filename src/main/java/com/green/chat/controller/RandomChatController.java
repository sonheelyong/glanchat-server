package com.green.chat.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.green.chat.dto.ChatDTO;
import com.green.chat.model.ChatEntity;
import com.green.chat.model.MessageEntity;
import com.green.chat.persistence.ChatRepository;
import com.green.chat.service.ChatService;
import com.green.chat.service.MessageService;
import com.green.chat.service.UserService;

@RestController
@RequestMapping("/randomchat")
public class RandomChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> randomChat(@RequestParam String username){
        List<ChatEntity> room = chatService.findByUsername2(null);
        if(room.size() == 0){
            String roomId = UUID.randomUUID().toString();
            ChatEntity chatroom = ChatEntity.builder()
                                  .roomid(roomId)
                                  .username1(username)
                                  .status("finding")
                                  .build();
            chatService.save(chatroom);
            return ResponseEntity.ok(chatroom);}
         else{  
                ChatEntity getroom = room.get(0);
                getroom.setUsername2(username);
                getroom.setStatus("match");
                chatService.save(getroom);
                return ResponseEntity.ok(getroom);
            }

        }

    @GetMapping("/status")
    public String getStatus(@RequestParam String roomid){
        ChatEntity entity = chatService.findByRoomid(roomid);
        String stat = entity.getStatus();
        System.out.println("스텟"+stat);
        return stat;
    }

    @DeleteMapping
    public void deleteRoom(@RequestParam String roomid){
        chatService.delete(roomid);
    }
    
    @GetMapping("/message")
    public ResponseEntity<?> getmsg(@AuthenticationPrincipal String useremail, @RequestParam String otherName){
        
        String username = userService.getUserName(useremail);

        List<MessageEntity> list1 = messageService.findBySenderNameAndReceiverName(username,otherName);
        List<MessageEntity> result = new ArrayList<>(list1);
       
        return ResponseEntity.ok().body(result);
    }


    }

    

