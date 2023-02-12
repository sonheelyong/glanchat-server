package com.green.chat.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.chat.model.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, String> {

	List<ChatEntity> findByUsername2(Object object);

    ChatEntity findByRoomid(String roomid);

    void deleteByRoomid(String roomid);
    
}
