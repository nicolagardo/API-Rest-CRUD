package com.tutorial.crud.security.controller;

import com.tutorial.crud.security.dto.JwtDto;
import com.tutorial.crud.security.dto.LoginUser;
import com.tutorial.crud.security.dto.NewUser;
import com.tutorial.crud.security.entity.Role;
import com.tutorial.crud.security.entity.User;
import com.tutorial.crud.security.enums.RoleName;
import com.tutorial.crud.security.jwt.JwtProvider;
import com.tutorial.crud.security.service.RoleService;
import com.tutorial.crud.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

     @Autowired
    PasswordEncoder passwordEncoder;

     @Autowired
    AuthenticationManager authenticationManager;

     @Autowired
    UserService userService;

     @Autowired
    RoleService roleService;

     @Autowired
     JwtProvider jwtProvider;
     @PostMapping("/new")

     public ResponseEntity<?> newuser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
         if (bindingResult.hasErrors())
             return new ResponseEntity<>("Campos mal puestos o email inválido", HttpStatus.BAD_REQUEST);
         if(userService.existByUsername(newUser.getUserName()))
             return new ResponseEntity("userName ya existe", HttpStatus.BAD_REQUEST);
         if(userService.existByEmail(newUser.getEmail()))
             return new ResponseEntity("email ya existe", HttpStatus.BAD_REQUEST);
        User user =
                new User(newUser.getName(), newUser.getUserName(), newUser.getEmail(), passwordEncoder.encode(newUser.getPassword()));
         Set<Role> roles = new HashSet<>();
         roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
         if (newUser.getRoles().contains("admin"))
             roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
         user.setRoles(roles);
         userService.save(user);
         return new ResponseEntity("usuario gurdado", HttpStatus.CREATED);
     }
    @PostMapping("/login")
     public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult) {
         if(bindingResult.hasErrors())
             return new ResponseEntity("Campos mal puestos o email inválido", HttpStatus.BAD_REQUEST);

         Authentication authentication =
                 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));
         SecurityContextHolder.getContext().setAuthentication(authentication);
         String jwt = jwtProvider.generateToken(authentication);
         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         JwtDto jwDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
         return new ResponseEntity<>(jwDto, HttpStatus.OK);
     }

}
