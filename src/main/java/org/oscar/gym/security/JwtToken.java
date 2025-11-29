package org.oscar.gym.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtToken {

    @Value("${KEY_ALGO}")
    private String secret;

    public String create(String username){
       return JWT.create()
               .withSubject(username)
               .withExpiresAt(new Date(System.currentTimeMillis()+
                       TimeUnit.HOURS.toMillis(1))).sign(Algorithm.HMAC256(secret));
    }


    public boolean isValid(String jwt){
        try {
            JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUser(String jwt){
       return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(jwt)
                .getSubject();
    }
}
