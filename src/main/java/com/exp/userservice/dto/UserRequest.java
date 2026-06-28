package com.exp.userservice.dto;

import com.exp.userservice.enums.Gender;

public record UserRequest(String emailId, String password, String firstName, String lastName, Gender gender,
		 String mobileNo, String timezone) {

}
