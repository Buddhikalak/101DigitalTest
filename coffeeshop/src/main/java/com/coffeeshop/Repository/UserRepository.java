package com.coffeeshop.Repository;

import com.coffeeshop.EntityClasses.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    public UserEntity findByUserName(String username);
    public UserEntity findByToken(String token);
}
