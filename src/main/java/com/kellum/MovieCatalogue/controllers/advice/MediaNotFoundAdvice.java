package com.kellum.MovieCatalogue.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kellum.MovieCatalogue.exceptions.MediaNotFoundException;

@ControllerAdvice
public class MediaNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(MediaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String mediaNotFoundHandler(MediaNotFoundException mex) {
        return mex.getMessage();
    }
}
