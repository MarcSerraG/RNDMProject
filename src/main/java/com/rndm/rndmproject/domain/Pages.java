package com.rndm.rndmproject.domain;

import com.rndm.rndmproject.persistence.ThreadDAO;



public class Pages {

    private int numberPages = 0;


    public Pages(){
    }

    public int getNumberPages(ThreadDAO threadDAO) {
        int totalThreads = threadDAO.getTotalThreads();
        numberPages = totalThreads / 10;
        if(totalThreads % 10 > 0) numberPages += 1;
        if(numberPages == 0) numberPages = 1;
        return numberPages;
    }


}
