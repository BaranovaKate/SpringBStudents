package by.baranova.springbootstudents.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleRuntimeException(EntityNotFoundException exception) {
        final ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.setViewName("students/error");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        return modelAndView;
    }
}