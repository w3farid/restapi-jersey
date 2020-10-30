package com.jee.round44;

import java.util.List;

public class ApiErrorResponse {
	
	String status;
	String message;
	List<String> errors;
	Object data;
	public ApiErrorResponse(String status, String message, List<String> errors, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
	
}
