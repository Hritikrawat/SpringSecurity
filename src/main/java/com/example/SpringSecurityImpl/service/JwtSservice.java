package com.example.SpringSecurityImpl.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtSservice {

    private static final Logger logger = LoggerFactory.getLogger(JwtSservice.class);

    private String ourkey ="";
    JwtSservice ()
    {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            this.ourkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username)
    {
        logger.info("Generating Token");
        Map<String,Object> claims = new HashMap<>();

        //        return Jwts.claims()
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*1))
                .and()
                .signWith(getKey()).compact();



    }


    private Key getKey()
    {

        byte [] key = Base64.getDecoder().decode(ourkey) ;
        return Keys.hmacShaKeyFor(key);
    }
}
