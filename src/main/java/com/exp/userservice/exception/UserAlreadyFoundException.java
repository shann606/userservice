package com.exp.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAlreadyFoundException  extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6261048761552892765L;
	private final String reason;

}
