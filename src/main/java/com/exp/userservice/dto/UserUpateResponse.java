package com.exp.userservice.dto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record UserUpateResponse(UUID id, String username,String password, String firstname, String lastname, String mobileno,
		String userstatus, Set<String> userroles, List<String> status, List<String> roles, String timezone) {

}
