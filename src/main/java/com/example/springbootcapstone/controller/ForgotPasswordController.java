package com.example.springbootcapstone.controller;

import com.example.springbootcapstone.Document.PasswordDto;
import com.example.springbootcapstone.service.ForgotPasswordService;
import com.example.springbootcapstone.service.MailSendingService;
import com.example.springbootcapstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/forgot")
public class ForgotPasswordController {

    @Autowired
    MailSendingService mailSendingService;

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @Autowired
    UserService userService;

    @PostMapping("/reset")
    @ResponseBody
    public String forgotPass(@RequestBody String username)  {
        if(forgotPasswordService.isFound(username)){
            String generatedToken = forgotPasswordService.generateToken(username);
            mailSendingService.forgotMailSender(username, generatedToken);
            return "mail sent";
        }
        return "user not found!";
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public String changePassword(@RequestParam("token") String generatedToken){
        if(forgotPasswordService.allow(generatedToken)){
            return "you can change now  \n http://localhost:8080/change";
        }

       return "the link is broken";
    }

    @RequestMapping("/change")
    @ResponseBody
    public String change(@Valid @RequestBody PasswordDto pass){
        if(forgotPasswordService.isFound(pass.getUsername())){
            if(forgotPasswordService.change(pass.getUsername(),pass.getPassword())){
                    return "changed successfully";
            }
            return "you are not allowed to change";
        }
        return "user Not found";
    }

}
