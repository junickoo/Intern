package com.api.appsimar.exception.servererror;

import java.util.HashMap;

import org.springframework.http.HttpStatus;

public class ServerErrorRequestException extends RuntimeException {
	
	private final HashMap<String, String> message_error;
	private final HttpStatus httpstatus;
	private Object output_schema;
	
	public ServerErrorRequestException(String ErrorCode,HashMap<String, String> message_error, HttpStatus httpstatus,
			Object output_schema) {
		super(ErrorCode);
		this.message_error = message_error;
		this.httpstatus = httpstatus;
		this.output_schema = output_schema;
	}

	public ServerErrorRequestException(String ErrorCode,HashMap<String, String> message,HttpStatus httpstatus) {
		super(ErrorCode);
		this.message_error=message;
		this.httpstatus=httpstatus;
	}
	
	public HashMap<String, String> getMessageError() {
		return message_error;
	}

	public HttpStatus getHttpstatus() {
		return httpstatus;
	}

	public Object getOutput_schema() {
		return output_schema;
	}

	public void setOutput_schema(Object output_schema) {
		this.output_schema = output_schema;
	}
	
	
	

}
