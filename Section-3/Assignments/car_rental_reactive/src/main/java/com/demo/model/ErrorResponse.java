package com.demo.model;

public class ErrorResponse {

	String errorCode;
	String errorDescription;
		
	public ErrorResponse(String errorCode, String errorDescription) {
		super();
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	@Override
	public String toString() {
		return "ErrorResponse [errorCode=" + errorCode + ", errorDescription=" + errorDescription + "]";
	}
	
	
}
