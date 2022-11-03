package com.example.springbootcapstone.Document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;
@Document
public class Token {
    @Id
    String tokenId;
    Date createdDate;
    String generatedToken;
    User user;

    public Token(User user) {
        this.user = user;
        createdDate=new Date();
        generatedToken= UUID.randomUUID().toString();
    }

    public String getGeneratedToken() {
        return generatedToken;
    }

    public void setGeneratedToken(String generatedToken) {
        this.generatedToken = generatedToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
