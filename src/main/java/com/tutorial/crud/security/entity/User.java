package com.tutorial.crud.security.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
@Entity
public class User {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     @NotNull
    private String name;
     @NotNull
     @Column(unique = true)
    private String userName;
     @NotNull
     @Email
    private String email;
     @NotNull
    private String password;
     @NotNull
     @ManyToMany (fetch = FetchType.EAGER)
     @JoinTable(name = "user_role", joinColumns = @JoinColumn (name = "user_id"),
     inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles =new HashSet<>();

    public User() {
    }

    public User(String name, String userName, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
