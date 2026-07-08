package com.exp.userservice.dto;

import java.util.List;
import java.util.UUID;

import com.exp.userservice.enums.UserRoles;
import com.exp.userservice.enums.UserStatus;

public record UserFullDetailsRequest(UUID id, String username, String firstname, String lastname, String mobileno,
		String userStatus, List<String> userRoles, List<UserStatus> status, List<UserRoles> roles , String timezone,String updatedby) {

}
