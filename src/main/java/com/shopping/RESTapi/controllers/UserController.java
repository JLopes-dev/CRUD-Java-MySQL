package com.shopping.RESTapi.controllers;

import com.shopping.RESTapi.dtos.DTODadosLogin;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    AuthenticationManager manager;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DTODadosLogin data){
        Authentication auth = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        manager.authenticate(auth);
        return ResponseEntity.ok().build();
    }

}
