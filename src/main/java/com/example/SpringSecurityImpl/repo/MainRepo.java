package com.example.SpringSecurityImpl.repo;

import com.example.SpringSecurityImpl.entity.Janta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainRepo extends MongoRepository<Janta,String>
{
    Janta findByJname(String name);
}
