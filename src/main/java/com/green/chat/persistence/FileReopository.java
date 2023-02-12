package com.green.chat.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.chat.model.UserFileEntity;

@Repository
public interface FileReopository extends JpaRepository<UserFileEntity, String> {



    
}
