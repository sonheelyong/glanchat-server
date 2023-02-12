package com.green.chat.service;

import com.green.chat.model.UserEntity;
import com.green.chat.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity) {
        if (userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String email = userEntity.getEmail();
        if (userRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
        final UserEntity originalUser = userRepository.findByEmail(email);

        // matches 메서드를 이용해 패스워드가 같은지 확인
        if (originalUser != null && encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }

    public UserEntity findByPhonenumber(final String phonenumber) {
        final UserEntity user = userRepository.findByPhonenumber(phonenumber);
        return user;
    }

    public UserEntity findByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user;
    }

    public void save(UserEntity user) {
        userRepository.save(user);
    }

    public String getUserImg(String email) {
      return userRepository.getUserImg(email);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String getUserName(String email) {
      return userRepository.getUserName(email);
    }

    public List<UserEntity> getAllUser() {
        return userRepository.getAllUser();
    }

}
