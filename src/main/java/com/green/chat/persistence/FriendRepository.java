package com.green.chat.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.chat.model.FriendListEntity;

@Repository
public interface FriendRepository extends JpaRepository<FriendListEntity, String> {

    FriendListEntity findByEmailAndRequireemail(String email, String require_email);

    List<FriendListEntity> findByEmailAndRequirecheck(String user_email, String string);

    int countByEmailAndRequirecheck(String user_email, String stirng);

    List<FriendListEntity> findByEmailAndRequirecheck(String user_email, String string, Pageable pageable);

}
