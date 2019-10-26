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



        System.out.println("Adding comment");
        Comment comment = new Comment("megaZambrano", "That's nice", null);
        thread.addComment(comment);
        System.out.println("Testing added comment");
        for (Comment cmt : thread.getComments()) System.out.println(cmt.getContent());
        System.out.println("Removing comment");
        thread.removeComment(comment);
        System.out.println("Testing removed comments");
        for (Comment cmt : thread.getComments()) System.out.print(cmt.getContent());


    }
}
