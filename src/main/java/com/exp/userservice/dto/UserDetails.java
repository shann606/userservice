package com.exp.userservice.dto;

import java.util.List;

import com.exp.userservice.enums.UserRoles;

public record UserDetails(String username, String password, List<UserRoles> roles) {

}
