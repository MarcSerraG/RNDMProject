package com.rndm.rndmproject;

import com.rndm.rndmproject.Controller.UserUseCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class RndmProjectApplication implements CommandLineRunner {

    @Autowired
    UserUseCases user;

    public static void main(String[] args) {
        SpringApplication.run(RndmProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println(user.isPrivate("Ricard"));
    }
}
