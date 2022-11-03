package com.example.springbootcapstone.service;

import com.example.springbootcapstone.Document.User;
import com.example.springbootcapstone.repository.UserRepository;
import com.example.springbootcapstone.service.interfaces.UserServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceDaoImpl implements UserServiceDao {

    @Autowired
    UserRepository userRepository;
    @Override
    public User findByUserName(String userName) {
        List<User> all = userRepository.findAll();
        for(User user:all){
            if(user.getUsername().equalsIgnoreCase(userName)){
                return user;
            }
        }
        return null;
    }


}
