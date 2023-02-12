package com.green.chat.persistence;

import com.green.chat.model.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByEmail(String email);

    Boolean existsByEmail(String email);

    UserEntity findByEmailAndPassword(String email, String password);

    UserEntity findByPhonenumber(String phonenumber);

    @Query(value="SELECT USERFILEID FROM USER_TB WHERE EMAIL = ?", nativeQuery = true)
    String getUserImg(String email);

    UserEntity findByUsername(String username);

    @Query(value="SELECT USERNAME FROM USER_TB WHERE EMAIL = ?", nativeQuery = true)
    String getUserName(String email);

    @Query(value="SELECT * FROM USER_TB ", nativeQuery = true)
    List<UserEntity> getAllUser();

}
