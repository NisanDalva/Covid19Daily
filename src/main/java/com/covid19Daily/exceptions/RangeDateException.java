package com.covid19Daily.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RangeDateException extends RuntimeException {
    
    public RangeDateException() {
	}

	public RangeDateException(String message) {
		super(message);
	}
}
