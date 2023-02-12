package com.green.chat.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.chat.model.AlarmEntity;
import com.green.chat.persistence.AlarmRepository;

@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    public void create(AlarmEntity alarmEntity) {
        alarmRepository.save(alarmEntity);
    }

    public AlarmEntity findById(String user_email) {
        return alarmRepository.getById(user_email);
    }

    public void save(AlarmEntity alarmEntity) {
        alarmRepository.save(alarmEntity);
    }

    public Optional<AlarmEntity> getAlarm(String user_email) {

        return alarmRepository.findById(user_email);
    }

    
    
}
