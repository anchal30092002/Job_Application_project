package com.Anchal.firstjobApp.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //Spring Boot annotation used for global exception handling. it is a combination of @ControllerAdvice(applies to all controllers globally) +@ResponseBody(returns response in JSON format) . after this no need to write try catch in controller to handle exception
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) //It is a method-level annotation that tells Spring:“If this exception occurs, handle it using this method.”
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<String> handleConflict(ResourceConflictException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // 409
    }

  /**  @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }**/
}
