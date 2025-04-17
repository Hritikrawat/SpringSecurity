package com.example.SpringSecurityImpl.service;

import com.example.SpringSecurityImpl.entity.Janta;
import com.example.SpringSecurityImpl.repo.MainRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class TryingDocumentService {

//    @Autowired
//    private Janta j ;

    @Autowired
    private MainRepo mainRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public Document save(Janta ja)
    {

//        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map =  objectMapper.convertValue(ja,Map.class);

//        Document d = new Document();
//        map.append("From","Document");

        map.put("From","DocumentSaving");

        Document d = new Document(map);
        mongoTemplate.getCollection("SpringSecurity")
                .insertOne(d);

        return d;
    }


}
