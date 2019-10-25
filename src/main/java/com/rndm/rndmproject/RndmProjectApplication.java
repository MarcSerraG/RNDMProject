package com.rndm.rndmproject;

import com.rndm.rndmproject.domain.Category;
import com.rndm.rndmproject.domain.Comment;
import com.rndm.rndmproject.domain.Thread;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RndmProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RndmProjectApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        Category category = new Category("Gay Marriage");
        Thread thread = new Thread("Sample1", "This is a sample thread", null, "megaZambrano", null, category);

        //Test thread
        System.out.println("Testing title");
        System.out.println(thread.getTitle());
        System.out.println("Testing date");
        System.out.println(thread.getDate());
        System.out.println("Testing category");
        System.out.println(thread.getCategory().getName());
        System.out.println("Testing comment allocation");
        System.out.println(thread.getComments());
        System.out.println("Testing downlvotes");
        System.out.println(thread.getDownvotes());
        System.out.println("Testing upvotes");
        System.out.println(thread.getUpvotes());
        System.out.println("Testing username");
        System.out.println(thread.getUsername());
        System.out.println("Testing ID");
        System.out.println(thread.getID());
        System.out.println("Testing ID");

        System.out.println("Adding comment");
        Comment comment = new Comment();


    }
}
