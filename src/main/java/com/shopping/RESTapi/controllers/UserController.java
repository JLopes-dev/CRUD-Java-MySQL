package com.shopping.RESTapi.controllers;
import com.shopping.RESTapi.dtos.DTOJwt;
import com.shopping.RESTapi.dtos.DTOLogin;
import com.shopping.RESTapi.models.User;
import com.shopping.RESTapi.repositorys.UserRepository;
import com.shopping.RESTapi.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid DTOLogin data){
        var checkAuth = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = manager.authenticate(checkAuth);
        var token = tokenService.createTokenJWT((User) auth.getPrincipal());
        return ResponseEntity.ok(new DTOJwt(token));
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody DTOLogin data, UriComponentsBuilder uriComponentsBuilder){
       UserDetails findUser = repository.findByLogin(data.login());
       if (findUser != null){
           return ResponseEntity.badRequest().build();
       }
       UserDetails userSaved = repository.save(new User(data.login(), new BCryptPasswordEncoder().encode(data.password())));
       return ResponseEntity.status(201).body(new DTOLogin(userSaved.getUsername(), userSaved.getPassword()));
    }

}
