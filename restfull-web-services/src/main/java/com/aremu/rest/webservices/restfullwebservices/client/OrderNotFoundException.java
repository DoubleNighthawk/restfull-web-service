package com.aremu.rest.webservices.restfullwebservices.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

	public	OrderNotFoundException(Long id) {
		super( "Could not find employee " + id );
	}
		
}
	
	
