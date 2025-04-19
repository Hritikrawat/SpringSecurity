package com.example.SpringSecurityImpl.service;

import com.example.SpringSecurityImpl.entity.Janta;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtSservice {


//    @Autowired
//    private UserDetails userDetails;
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
                .expiration(new Date(System.currentTimeMillis() + 60*60*10))
                .and()
                .signWith(getKey())
                .compact();



    }


    private SecretKey getKey()
    {

        byte [] key = Base64.getDecoder().decode(ourkey) ;
        return Keys.hmacShaKeyFor(key);
    }

    public String extractUsername(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    //deprecated
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails jan)
    {
        final String userName = extractUsername(token);
        return (userName.equals(jan.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){

        return extractExpiration(token). before(new Date());
    }
    private Date extractExpiration(String token)
    {
        return extractClaim(token,Claims::getExpiration);
    }
}
