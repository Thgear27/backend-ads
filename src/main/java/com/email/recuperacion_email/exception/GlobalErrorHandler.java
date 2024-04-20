package com.email.recuperacion_email.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllException(Exception ex, WebRequest req) {
		ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ModelNotFoundException.class) //INDICAR EL NOMBRE DE EXCEPCION
    public ResponseEntity<ErrorResponse> handleModelNotFoundException(ModelNotFoundException ex, WebRequest req){
        ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND.value(),LocalDateTime.now(),ex.getMessage(),req.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(SQLException.class) //INDICAR EL NOMBRE DE EXCEPCION
    public ResponseEntity<ErrorResponse> handleSQLException(ModelNotFoundException ex, WebRequest req){
        ErrorResponse err = new ErrorResponse(HttpStatus.CONTINUE.value(), LocalDateTime.now(),ex.getMessage(),req.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }

	//PARA ARGUMENTOS NO VALIDOS
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String message = ex.getBindingResult().getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": "+error.getDefaultMessage())
				.collect(Collectors.joining(" ; "));
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),message,request.getDescription(false));
		return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND.value(),LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}

}
