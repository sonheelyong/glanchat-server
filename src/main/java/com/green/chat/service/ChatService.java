package com.green.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.chat.model.ChatEntity;
import com.green.chat.persistence.ChatRepository;

@Service

public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public List<ChatEntity> findByUsername2(Object object) {
        List<ChatEntity> room = chatRepository.findByUsername2(null);
        return room;
    }

    public void save(ChatEntity chatroom) {
        chatRepository.save(chatroom);
    }

    public ChatEntity findByRoomid(String roomid) {
        ChatEntity entity = chatRepository.findByRoomid(roomid);
        return entity;
    }

    public void delete(String roomid) {
        chatRepository.deleteByRoomid(roomid);
    }
    
}
