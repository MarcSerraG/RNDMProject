package com.rndm.rndmproject.WebController;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandlingController implements ErrorController {

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Model model, HttpServletRequest request, Exception ex){
        String url = request.getRequestURL().toString();
        return "error";
    }

    @ExceptionHandler(InternalError.class)
    public String internalServerException(Model model, HttpServletRequest request, Exception ex){
        String url = request.getRequestURL().toString();
        return "error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }



}
