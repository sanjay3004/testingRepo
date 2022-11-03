package com.example.springbootcapstone.service.interfaces;

import com.example.springbootcapstone.Document.User;

public interface UserServiceDao {

    User findByUserName(String userName);
}
