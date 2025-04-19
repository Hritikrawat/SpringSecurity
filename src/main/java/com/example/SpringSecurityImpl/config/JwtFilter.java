package com.example.SpringSecurityImpl.config;

import com.example.SpringSecurityImpl.service.JwtSservice;
import com.example.SpringSecurityImpl.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter
{

    @Autowired
    private ApplicationContext context;

    @Autowired
    private JwtSservice jwtSservice;

    private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
//        header = Bearerdsgdfhgjhjthg.23rgfvbghsryjjhfnbfh.r435thrtgdfbd

        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

                if(header!=null && header.startsWith("Bearer")){
                    token =  header.substring(7);
                    username = jwtSservice.extractUsername(token);
                }

                if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
                {
                    UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
                    if(jwtSservice.validateToken(token,userDetails))
                    {
                        UsernamePasswordAuthenticationToken authToken =new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        logger.info("Token Validated");
                    }
                }

                filterChain.doFilter(request,response);
    }
}
