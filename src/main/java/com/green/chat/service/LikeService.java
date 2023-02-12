package com.green.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import com.green.chat.model.LikeEntity;
import com.green.chat.persistence.LikeRepository;

@Service
public class LikeService {
    @Autowired
   private LikeRepository likeRepository;

    public void save(LikeEntity entity) {
        likeRepository.save(entity);
    }

    public LikeEntity findByBnoAndEmail(String bno, String email) {
        LikeEntity entity = likeRepository.findByBnoAndEmail(bno,email);
        return entity;
    }

    public void delete(LikeEntity check) {
        likeRepository.delete(check);
    }

    public int countByBno(String bno) {
        int count = likeRepository.countByBno(bno);
        return count;
    }
    
}
