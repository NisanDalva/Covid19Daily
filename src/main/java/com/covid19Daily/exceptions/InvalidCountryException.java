package com.covid19Daily.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidCountryException extends RuntimeException {

    public InvalidCountryException() {
	}

	public InvalidCountryException(String message) {
		super(message);
	}
    
}
