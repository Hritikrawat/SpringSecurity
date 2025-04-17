package com.example.SpringSecurityImpl.controllers;

import com.example.SpringSecurityImpl.entity.Janta;
import com.example.SpringSecurityImpl.repo.MainRepo;
import com.example.SpringSecurityImpl.service.TryingDocumentService;
import com.example.SpringSecurityImpl.service.UserService;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TryingDocumentService docService;

    private final MainRepo mainRepo;

    public UserController(MainRepo mainRepo) {
        this.mainRepo = mainRepo;
    }

    @GetMapping("/check")
    public String check() {
        return "Working";
    }

    @GetMapping("/getAll")
    public List<Janta> showAll() {
        return mainRepo.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity add(@RequestBody Janta jan, @RequestHeader(value = "doc", required = false) String doc) {

        if (doc != null && !doc.isEmpty()) {
            Document d = docService.save(jan);
            if (d != null) {
                return ResponseEntity.ok().body(d);
            }
        } else if (true == userService.save(jan)) {
            return ResponseEntity.ok(jan);
        }
        return ResponseEntity.status(500).body("User not added");


    }

    @PostMapping("/login")
    public String login(@RequestBody Janta j) {
        logger.info("Login API Triggered");
        String token = userService.verify(j);
        if(!token.isEmpty() && token!=null)
            return token;
        return "Token is Empty";
    }


}
