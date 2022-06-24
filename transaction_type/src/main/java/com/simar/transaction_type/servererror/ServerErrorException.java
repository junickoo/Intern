package com.api.appsimar.exception.servererror;

import java.util.HashMap;

public class ServerErrorException  {

	private final String error_code;
	private final HashMap<String, String> error_message;
	private Object output_schema;
	
	public ServerErrorException(String error_code, HashMap<String, String> error_message,Object output_schema) {
		super();
		this.error_code = error_code;
		this.error_message = error_message;
		this.output_schema=output_schema;
	}
	
	public ServerErrorException(String error_code, HashMap<String, String> error_message) {
		super();
		this.error_code = error_code;
		this.error_message = error_message;
	}
	
	public String getError_code() {
		return error_code;
	}

	public HashMap<String, String> getError_message() {
		return error_message;
	}

	public Object getOutput_schema() {
		return output_schema;
	}

	public void setOutput_schema(Object output_schema) {
		this.output_schema = output_schema;
	}
	
	
	

}
