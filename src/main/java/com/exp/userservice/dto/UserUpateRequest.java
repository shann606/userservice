package com.exp.userservice.dto;

public record UserUpateRequest(String username,String password, String mobileno, String statusupdate, String deleteroles,
		String addrole, String timezone) {

}
