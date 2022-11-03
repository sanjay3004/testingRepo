package com.example.springbootcapstone.Document;

import com.example.springbootcapstone.Document.User;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

public class ForgotToken {
    @Id
    String tokenId;
    Date createdDate;
    String generatedToken;
    User user;

    public ForgotToken(User user) {
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
