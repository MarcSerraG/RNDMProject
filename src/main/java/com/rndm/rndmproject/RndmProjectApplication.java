package com.rndm.rndmproject;

import com.rndm.rndmproject.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RndmProjectApplication implements CommandLineRunner {

    @Autowired
    UserDAO userDAO;

    public static void main(String[] args) {
        SpringApplication.run(RndmProjectApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {



    }
}
