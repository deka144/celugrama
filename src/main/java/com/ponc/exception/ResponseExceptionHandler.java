package com.ponc.exception;

import com.ponc.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler {


    //    @ExceptionHandler(Exception.class)
//    public ResponseEntity<CustomErrorRecord> handleDefaultExceptions(Exception ex, WebRequest request) {
//        CustomErrorRecord err= new CustomErrorRecord(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
//        return  new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    // Manejador de respaldo: atrapa cualquier excepcion que no tenga
    // uno mas especifico abajo. Responde 500 (igual status que ya tenia).
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleDefaultExceptions(Exception ex, WebRequest request) {
        log.error("Error no controlado en {}", request.getDescription(false), ex);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


//    @ExceptionHandler(ModelNotFoundException.class)
//    public ResponseEntity<CustomErrorRecord> handleModelNotFoundException(ModelNotFoundException ex, WebRequest request) {
//        CustomErrorRecord err = new CustomErrorRecord(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
//    }

    // Se dispara cuando un service lanza esto (en tu CRUDImpl:
    // findById, update, deleteById) con un ID inexistente.
    // Responde 404, igual que ya tenia.
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleModelNotFoundException(
            ModelNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiResponse.error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }


//    @ExceptionHandler(ArithmeticException.class)
//    public ResponseEntity<CustomErrorRecord> handleArithmeticException(ArithmeticException ex, WebRequest request) {
//        CustomErrorRecord err = new CustomErrorRecord(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>(err, HttpStatus.NOT_ACCEPTABLE);
//
//    }


    // Se dispara con errores matematicos (ej. division entre cero).
    // Responde 406, igual que ya tenia.
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleArithmeticException(
            ArithmeticException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiResponse.error(ex.getMessage()),
                HttpStatus.NOT_ACCEPTABLE);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<CustomErrorRecord> handleMethodArgumentNotVAlidException(MethodArgumentNotValidException ex, WebRequest request) {
//        CustomErrorRecord err = new CustomErrorRecord(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
//
//        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
//
//    }


    // Se dispara cuando falla la validacion de un @RequestBody marcado
    // con @Valid. Responde 400, igual que ya tenia.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleMethodArgumentNotVAlidException(
            MethodArgumentNotValidException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    // Nuevo: atrapa las ApiException que lances a proposito desde un
    // service (ver el Extra, al final del manual), respetando el status
    // que tu mismo elegiste al lanzarla.
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleApiException(
            ApiException ex, WebRequest request) {
        return new ResponseEntity<>(
                ApiResponse.error(ex.getMessage()), ex.getStatus());

    }
}


