package com.green.chat.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.chat.model.AlarmEntity;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, String>{

    
    
}
