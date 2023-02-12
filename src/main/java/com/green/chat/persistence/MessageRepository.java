package com.green.chat.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.green.chat.model.MessageEntity;


public interface MessageRepository extends JpaRepository<MessageEntity,Long> {
    
@Query(value="SELECT * FROM(SELECT * FROM MESSAGE_TB WHERE RECEIVER_NAME = ?1 AND SENDER_NAME=?2 UNION SELECT * FROM MESSAGE_TB WHERE RECEIVER_NAME = ?2 AND SENDER_NAME=?1) ORDER BY MESSAGEDATE", nativeQuery = true)
    List<MessageEntity> findBySenderNameAndReceiverName(String username1, String username2);

    // List<MessageEntity> findBySenderNameAndReceiverNameOrReceiverNameAndSenderName(String username1, String username2, String username3, String username4);
    
}
