package com.Guzman.UploadFile.domain.service;

import com.Guzman.UploadFile.persistence.crud.UserCrudRepository;
import com.Guzman.UploadFile.persistence.entity.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSegurityService implements UserDetailsService {

    private final UserCrudRepository userCrudRepository;

    public UserSegurityService(UserCrudRepository userCrudRepository) {
        this.userCrudRepository = userCrudRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = this.userCrudRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Client with email " + email + " not found.");
        }

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
