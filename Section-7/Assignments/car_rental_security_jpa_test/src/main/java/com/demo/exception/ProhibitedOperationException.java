package com.demo.exception;

public class ProhibitedOperationException extends Exception
{
    
	private static final long serialVersionUID = -8651763654483775791L;

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
