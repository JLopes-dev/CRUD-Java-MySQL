package com.shopping.RESTapi.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.shopping.RESTapi.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTService {

    @Value("${spring.dotenv.jwt.sign}")
    private String jwtSign;

    public String tokenGeneration(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSign);
            return JWT.create()
                    .withIssuer("Product RestAPI")
                    .withSubject(user.getLogin())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Houve algum erro ao gerar o token: ", exception);
        }
    }

    public Instant expirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
