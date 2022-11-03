package com.example.springbootcapstone.service;

import com.example.springbootcapstone.repository.UserRepository;
import com.example.springbootcapstone.service.interfaces.UserServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserServiceDao userServiceDao;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userServiceDao.findByUserName(username);
    }



}
