package com.exp.userservice.dto;

import com.exp.userservice.enums.Gender;

public record UserResponse(String emailId, String firstName, String lastName, Gender gender,
		String mobileNo, String timezone) {

}
