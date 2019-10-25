package com.rndm.rndmproject.Controller;

import com.rndm.rndmproject.domain.Thread;
import com.rndm.rndmproject.persistence.ThreadDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("Controller")
public class ThreadUseCases {

    private final ThreadDAO threadDAO;

    public ThreadUseCases (ThreadDAO threadDAO){
        this.threadDAO = threadDAO;
    }
    
    public List<Thread>  findFirstTen (){
        return this.threadDAO.findFirstTen();
    }


}
