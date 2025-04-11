package com.example.SpringSecurityImpl.service;

import com.example.SpringSecurityImpl.entity.Janta;
import com.example.SpringSecurityImpl.entity.UserPrincipal;
import com.example.SpringSecurityImpl.repo.MainRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final MainRepo mainRepo;

    public MyUserDetailsService(MainRepo mainRepo) {
        this.mainRepo = mainRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Janta j = mainRepo.findByJname(username);
        if(j==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(j);
    }
}
