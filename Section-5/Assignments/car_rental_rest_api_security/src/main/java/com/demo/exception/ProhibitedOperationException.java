package com.demo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProhibitedOperationException extends Exception
{

	private static final long serialVersionUID = 7317682375319293316L;
	private final ErrorCode code;

	public ProhibitedOperationException(ErrorCode code) {
        super();
        this.code = code;
    }

	public ProhibitedOperationException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

	public ErrorCode getCode() {
		return this.code;
	}

	@Override
	public String toString() {
		return "[code=" + code + "]";
	}
	
	@Override
	public String getMessage(){
		return super.getMessage()+" "+this.toString();
	}

}
