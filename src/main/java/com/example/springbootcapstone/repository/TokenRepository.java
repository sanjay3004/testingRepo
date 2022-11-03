package com.example.springbootcapstone.repository;

import com.example.springbootcapstone.Document.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends MongoRepository<Token,String> {
    Token findByGeneratedToken(String generatedToken);
}
