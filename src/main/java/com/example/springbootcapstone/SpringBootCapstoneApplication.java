package com.example.springbootcapstone;

import com.example.springbootcapstone.Document.User;
import com.example.springbootcapstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootCapstoneApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCapstoneApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
    

}
