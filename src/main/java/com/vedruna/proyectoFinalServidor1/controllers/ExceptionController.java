package com.vedruna.proyectoFinalServidor1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vedruna.proyectoFinalServidor1.dto.ResponseDTO;

@RestControllerAdvice
public class ExceptionController {

    // Catching specific exceptions
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseDTO<String> response = new ResponseDTO<>("Bad Request", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
    }

    // Catching general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<String>> handleGeneralException(Exception ex) {
        ResponseDTO<String> response = new ResponseDTO<>("Internal Server Error", "An unexpected error occurred. Check that the path is correct and does not exist in the database and try again.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
