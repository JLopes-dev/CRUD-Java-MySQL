package com.shopping.RESTapi.controllers;
import com.shopping.RESTapi.dtos.DTOJwt;
import com.shopping.RESTapi.dtos.DTOLogin;
import com.shopping.RESTapi.infra.security.JWTService;
import com.shopping.RESTapi.models.User;
import com.shopping.RESTapi.repositorys.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    JWTService jwtService;

    @Autowired
    UserRepository repository;

    @Autowired
    BCryptPasswordEncoder bCrypt;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DTOLogin data){
        User user = repository.findByLogin(data.login());
        Boolean isCorrect = bCrypt.matches(data.password(), user.getPassword());
        if (!isCorrect) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody DTOLogin data, UriComponentsBuilder uriComponentsBuilder){
        String passwordHash = bCrypt.encode(data.password());
        User userSaved = repository.save(new User(data.login(), passwordHash));
        URI uri = uriComponentsBuilder.path("/login/register/{id}").buildAndExpand(userSaved.getId()).toUri();
        return ResponseEntity.created(uri).body(new DTOLogin(userSaved.getLogin(), userSaved.getPassword()));
    }

}
