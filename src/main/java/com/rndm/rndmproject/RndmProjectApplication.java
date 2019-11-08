package com.rndm.rndmproject;

import com.rndm.rndmproject.domain.Comment;
import com.rndm.rndmproject.persistence.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RndmProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RndmProjectApplication.class, args);
    }


    @Autowired
    CommentDAO commentDAO;


    @Override
    public void run(String... args) throws Exception {

        System.out.println(commentDAO.getComment("100").getFatherComment());
        System.out.println(commentDAO.getComment("101").getFatherComment());
        //System.out.println(father.getUsername());
        //Comment father2 = commentDAO.getComment("102");
        //System.out.println(father2.getContent());

    }
}
