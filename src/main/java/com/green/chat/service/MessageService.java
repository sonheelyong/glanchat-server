package com.green.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.chat.model.MessageEntity;
import com.green.chat.persistence.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void save(MessageEntity entity) {
        messageRepository.save(entity);
    }

    public List<MessageEntity> findBySenderNameAndReceiverName(String username1, String username2) {
        List<MessageEntity> list = messageRepository.findBySenderNameAndReceiverName(username1,username2);  
        return list;
    }

    // public List<MessageEntity> findBySenderNameAndReceiverNameOrReceiverNameAndSenderName(String username1,
    //         String username2) {
    //  List<MessageEntity> list = messageRepository.findBySenderNameAndReceiverNameOrReceiverNameAndSenderName(username1,username2,username2,username1);  
    //     return list;
    // }

}
