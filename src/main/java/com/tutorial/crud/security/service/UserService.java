package com.tutorial.crud.security.service;

import com.tutorial.crud.security.entity.User;
import com.tutorial.crud.security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepo userRepo;

    public Optional<User> getByUserame(String username){
        return userRepo.findByUserName(username);
    }
    public boolean existByUsername(String username) {
        return userRepo.existsByUserName(username);
    }
    public boolean existByEmail(String email) {
        return userRepo.existsByEmail(email);
    }
    public void save(User user){
        userRepo.save(user);
    }
}
