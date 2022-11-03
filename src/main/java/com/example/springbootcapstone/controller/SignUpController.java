package com.example.springbootcapstone.controller;

import com.example.springbootcapstone.Document.User;
import com.example.springbootcapstone.Document.PasswordDto;
import com.example.springbootcapstone.service.MailSendingService;
import com.example.springbootcapstone.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController()
@RequestMapping("/register")
public class SignUpController {

    @Autowired
    SignUpService signUpService;

    @Autowired
    MailSendingService mailSendingService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/newUser")
    public ResponseEntity<?> signUp( @Valid @RequestBody User user){
        boolean isAdded = signUpService.addUser(user);
        if(!isAdded){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already exists");
        }
        String generatedToken = signUpService.addToken(user);
        mailSendingService.mailSender(user.getUsername(),generatedToken);
        return ResponseEntity.status(HttpStatus.CREATED).body("Mail Sent");
    }

    @RequestMapping(value = "/confirm", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String generatedToken) {
        boolean isFound = signUpService.confirmAccount(generatedToken);
        if(!isFound){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found!");
        }
        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/register/confirm").toUriString());
        return ResponseEntity.created(uri).body("success");
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody PasswordDto passwordDto){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(passwordDto.getUsername(),passwordDto.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return ResponseEntity.ok("success");
    }

}
