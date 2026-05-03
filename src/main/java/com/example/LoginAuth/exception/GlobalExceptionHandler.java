package com.example.loginauth.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  //intercepta las excepciones que ocurran en cualquier RestController
public class GlobalExceptionHandler {
    
    //aca atrapa el error por si sale RuntimeException, se ejecuta el metodo de abajo
    @ExceptionHandler(RuntimeException.class)
    //el responseEntity permite controlar tanto el cuerpo del msje y el codigo de estado HTTP
    // se usa un mapa para que Sprin lo convierta en JSON
    // el hashMap es una estruc de datos para guardar info en pared de llave y valor (aca serie la llave el msje y el valor "usuario incorrecto")
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("mensaje", ex.getMessage());

        //aca se devuelve un 401 (no autorizado) en vez de un 403 o 500
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


}
