package com.Guzman.UploadFile.domain.service;

import com.Guzman.UploadFile.persistence.crud.UserCrudRepository;
import com.Guzman.UploadFile.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserCrudRepository userCrudRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity save(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userCrudRepository.save(user);
    }
    public UserEntity getId(int userId) {
        return userCrudRepository.findById(userId);
    }
    public UserEntity getprofile(String email){
        return userCrudRepository.findByEmail(email);
    }

}
