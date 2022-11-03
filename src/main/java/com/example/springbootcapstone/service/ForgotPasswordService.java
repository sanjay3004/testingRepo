package com.example.springbootcapstone.service;
import com.example.springbootcapstone.Document.ForgotToken;
import com.example.springbootcapstone.Document.User;
import com.example.springbootcapstone.repository.ForgotPasswordRepository;
import com.example.springbootcapstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {

    @Autowired
    ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    UserService userService;

    public String generateToken(String username){
        ForgotToken token=new ForgotToken(userRepository.findByUserName(username));
        forgotPasswordRepository.save(token);
        return token.getGeneratedToken();

    }

    public boolean allow(String generatedToken){
        ForgotToken token=forgotPasswordRepository.findByGeneratedToken(generatedToken);
        if(token==null){
            return false;
        }
        User user=token.getUser();
        user.setPasswordChangeable(true);
        forgotPasswordRepository.delete(token);
        userRepository.save(user);
        return true;
    }

    public boolean isFound(String userName){
        User user=userRepository.findByUserName(userName);
        if(user!=null){
            return true;
        }
        return false;
    }

    public boolean change(String userName,String pass){
        User user=userRepository.findByUserName(userName);
        System.out.println(userName);
        if(user.isPasswordChangeable()){
            user.setPassword(passwordEncoder.encode(pass));
            user.setPasswordChangeable(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
