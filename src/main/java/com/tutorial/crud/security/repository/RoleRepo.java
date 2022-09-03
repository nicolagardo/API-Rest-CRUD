package com.tutorial.crud.security.repository;

import com.tutorial.crud.security.entity.Role;
import com.tutorial.crud.security.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

Optional<Role> findByRoleName(RoleName roleName);

}
