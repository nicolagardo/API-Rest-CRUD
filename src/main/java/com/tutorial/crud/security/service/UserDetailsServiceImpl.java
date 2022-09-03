package com.tutorial.crud.security.service;

import com.tutorial.crud.security.entity.User;
import com.tutorial.crud.security.entity.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username ) throws UsernameNotFoundException {
        User user = userService.getByUserame(username).get();
        return UserPrincipal.build(user);
    }
}
