package com.green.chat.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.green.chat.model.UserFileEntity;
import com.green.chat.persistence.FileReopository;

@Service
public class FileService {
    
    @Autowired
    private FileReopository fileReopository;

    public void save(UserFileEntity file) {
        fileReopository.save(file);
    }

    public Optional<UserFileEntity> findById(String id) {
        Optional<UserFileEntity> file = fileReopository.findById(id);
        return file;
    }

    public UserFileEntity getById(String id) {
        return fileReopository.getById(id);
    }

}
