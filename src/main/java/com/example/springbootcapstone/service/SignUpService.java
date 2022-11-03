package com.example.springbootcapstone.service;

import com.example.springbootcapstone.Document.Token;
import com.example.springbootcapstone.Document.User;
import com.example.springbootcapstone.repository.TokenRepository;
import com.example.springbootcapstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignUpService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean addUser(User user) {
        User givenUser = userRepository.findByUserName(user.getUsername());
        List<Token> all = tokenRepository.findAll();
        for (Token token : all) {
            if (token.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
                tokenRepository.delete(token);
            }
        }
        if (givenUser == null || !givenUser.isEnabled()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            if (givenUser != null) {
                userRepository.delete(givenUser);
            }
            return true;

        } else {
            System.out.println(false);
            return false;
        }
    }

    public String addToken(User user) {
        Token token = new Token(user);
        tokenRepository.save(token);
        return token.getGeneratedToken();
    }

    public boolean confirmAccount(String generatedToken) {
        Token token = tokenRepository.findByGeneratedToken(generatedToken);
        if (token == null) {
            return false;
        }
        User currUser = token.getUser();
        currUser.setEnabled(true);
        tokenRepository.delete(token);
        List<User> all = userRepository.findAll();
        for (User user : all) {
            if (user.getUsername().equalsIgnoreCase(currUser.getUsername())) {
                userRepository.delete(user);
            }
        }
        userRepository.save(currUser);
        return true;
    }

}
