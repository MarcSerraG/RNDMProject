package com.rndm.rndmproject.Controller;

import com.rndm.rndmproject.domain.User;
import com.rndm.rndmproject.persistence.UserDAO;
import com.rndm.rndmproject.persistence.ThreadDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserUseCases {

    private UserDAO userDAO;

    public UserUseCases (UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getProfile(String username) {
        return this.userDAO.getProfile(username);
    }

    public List<User> getAllUsers() {
        return this.userDAO.getAllUsers();
    }

    public boolean isPrivate (String username) {
        return this.userDAO.isPrivate(username);
    }

    public int insertUser (User user) {
        return this.userDAO.insertUser(user);
    }

    public String getImage (String name){return userDAO.getImage(name);}

}
