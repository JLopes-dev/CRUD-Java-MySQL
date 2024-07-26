package com.shopping.RESTapi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.shopping.RESTapi.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${spring.dotenv.jwt.sign}")
    private String tokenSign;

    public String createTokenJWT(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSign);
            return JWT.create()
                    .withIssuer("Product RestAPI")
                    .withSubject(user.getLogin())
                    .withExpiresAt(expiresAt())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Houve um erro ao gerar o token JWT", exception);
        }
    }

    public String getSubject(String tokenJwt){
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSign);
            return JWT.require(algorithm)
                    .withIssuer("Product RestAPI")
                    .build()
                    .verify(tokenJwt)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token inválido ou expirado");
        }
    }


    public Instant expiresAt(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
