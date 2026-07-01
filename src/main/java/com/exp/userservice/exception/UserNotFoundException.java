package com.exp.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserNotFoundException extends Exception {
	/**
	* 
	*/
	private static final long serialVersionUID = -7672676715745666702L;

	private final String reason;

}
