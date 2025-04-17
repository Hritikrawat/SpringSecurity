package com.example.SpringSecurityImpl.service;

import com.example.SpringSecurityImpl.entity.Janta;
import com.example.SpringSecurityImpl.repo.MainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{

    @Autowired
    private AuthenticationManager auth;

    @Autowired
    private  JwtSservice jwtService;

    private final MainRepo mainRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
    public UserService(MainRepo mainRepo) {
        this.mainRepo = mainRepo;
    }


    public boolean save(Janta jan){
        jan.setJpass(bCryptPasswordEncoder.encode(jan.getJpass()));
        if(mainRepo.insert(jan)!=null)
                    return true;

        return false;
    }

    public String verify(Janta j)
    {
        Authentication authentication =
                auth.authenticate(new UsernamePasswordAuthenticationToken(j.getJname(),j.getJpass()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(j.getJname());

        }

        return "Not authenticated";

    }
}
