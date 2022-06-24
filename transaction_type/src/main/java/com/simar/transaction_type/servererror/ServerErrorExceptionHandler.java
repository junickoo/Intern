package com.api.appsimar.exception.servererror;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServerErrorExceptionHandler {
	
	@ExceptionHandler(value= {ServerErrorRequestException.class})
	public ResponseEntity<Object> handleServerErrorException(ServerErrorRequestException e){		
		
		ServerErrorException serverErrorException=new ServerErrorException(e.getMessage(), e.getMessageError(),e.getOutput_schema() );
		return new ResponseEntity<Object>(serverErrorException,e.getHttpstatus());
	}

}
