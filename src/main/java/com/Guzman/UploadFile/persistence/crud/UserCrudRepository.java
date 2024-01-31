package com.Guzman.UploadFile.persistence.crud;

import com.Guzman.UploadFile.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserCrudRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity findById(int userId);
    UserEntity findByEmail(String email);
}
