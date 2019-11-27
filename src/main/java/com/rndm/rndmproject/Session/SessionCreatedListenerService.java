package com.rndm.rndmproject.Session;

import com.rndm.rndmproject.Controller.UserUseCases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


@WebListener
public class SessionCreatedListenerService implements HttpSessionListener {

    private UserUseCases userUseCases;
    private static final Logger LOG= LoggerFactory.getLogger(SessionCreatedListenerService.class);

    public SessionCreatedListenerService(UserUseCases userUseCases) {
        this.userUseCases = userUseCases;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOG.info("New session is created.");
        se.getSession().setMaxInactiveInterval(900); //Session max time (seconds)
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        LOG.info(se.getSession().getAttribute("username") +" session destroyed. ");
        this.userUseCases.ChangeConnected((String) se.getSession().getAttribute("username"), 0);
    }

}