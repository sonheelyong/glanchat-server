package com.green.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.green.chat.model.FriendListEntity;
import com.green.chat.persistence.FriendRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FriendListService {

    @Autowired
    private FriendRepository friendRepository;

    public void create(FriendListEntity friendListEntity) {
        friendRepository.save(friendListEntity);
    }

    public FriendListEntity findByEmailAndRequireemail(String user_email, String opp_email) {
        return friendRepository.findByEmailAndRequireemail(user_email, opp_email);
    }

    public void save(FriendListEntity list) {
        friendRepository.save(list);
    }

    public void delete(FriendListEntity list) {
        friendRepository.delete(list);
    }

    public List<FriendListEntity> getRequestList(String user_email) {
        return friendRepository.findByEmailAndRequirecheck(user_email, "0");
    }

    public List<FriendListEntity> getRequireList(String user_email) {
        return friendRepository.findByEmailAndRequirecheck(user_email, "1");
    }

    public List<FriendListEntity> friendlist(String user_email) {
        return friendRepository.findByEmailAndRequirecheck(user_email, "2");
    }

    public List<FriendListEntity> blocklist(String user_email) {
        return friendRepository.findByEmailAndRequirecheck(user_email, "3");
    }

    public int countByEmailAndRequirecheck(String user_email, String string) {
      return friendRepository.countByEmailAndRequirecheck(user_email, string);
    }

    public List<FriendListEntity> getBlocklist(int postnum,int num, String user_email) {
        Pageable pageable = PageRequest.of(num, postnum);
        List<FriendListEntity> boardList = friendRepository.findByEmailAndRequirecheck(user_email,"3",pageable);
        return boardList;
    }

    

}
