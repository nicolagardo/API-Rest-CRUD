package com.tutorial.crud.security.service;

import com.tutorial.crud.security.entity.Role;
import com.tutorial.crud.security.enums.RoleName;
import com.tutorial.crud.security.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional

public class RoleService {

    @Autowired
    RoleRepo roleRepo;

    public Optional<Role> getByRoleName(RoleName roleName) {
        return roleRepo.findByRoleName(roleName);
    }
    public void save(Role role){
        roleRepo.save(role);
    }


}
