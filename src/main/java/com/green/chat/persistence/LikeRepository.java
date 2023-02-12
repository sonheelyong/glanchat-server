package com.green.chat.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.chat.model.LikeEntity;

public interface LikeRepository extends JpaRepository<LikeEntity ,String> {

    LikeEntity findByBnoAndEmail(String bno, String email);

    int countByBno(String bno);
    
}
