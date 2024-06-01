package com.bibliotheque.bibliotheque.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bibliotheque.bibliotheque.exceptions.ClientAlreadyExistException;
import com.bibliotheque.bibliotheque.exceptions.NoDataFoundException;

import io.swagger.v3.oas.annotations.Hidden;

@ControllerAdvice
@Hidden
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<Object> handleNoDataFoundException(NoDataFoundException exception, WebRequest request){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ClientAlreadyExistException.class)
	public ResponseEntity<Object> handleClientAlreadyExistException(ClientAlreadyExistException exception, WebRequest request){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
	}
}