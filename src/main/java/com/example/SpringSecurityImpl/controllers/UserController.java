package com.example.SpringSecurityImpl.controllers;

import com.example.SpringSecurityImpl.entity.Janta;
import com.example.SpringSecurityImpl.repo.MainRepo;
import com.example.SpringSecurityImpl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    private final MainRepo mainRepo ;

    public UserController(MainRepo mainRepo) {
        this.mainRepo = mainRepo;
    }

    @GetMapping("/check")
    public String check()
    {
        return "Working";
    }

    @GetMapping("/getAll")
    public List<Janta> showAll()
    {
        return mainRepo.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity add(@RequestBody Janta jan)
    {
//        if(Objects.isNull(mainRepo.insert(jan)))
//        {
//            return "Not Added";
//        }

        if(true==userService.save(jan))
        {
            return ResponseEntity.ok(jan);
        }
        return ResponseEntity.status(500).body("User not added");
    }

    @PostMapping("/login")
    public String login (@RequestBody Janta j){
        if(userService.verify(j))
                return "Sucess";

        return "Not success";
    }




}
