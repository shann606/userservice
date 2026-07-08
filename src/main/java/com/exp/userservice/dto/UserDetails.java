package com.exp.userservice.dto;

import java.util.List;
import java.util.UUID;

import com.exp.userservice.enums.UserRoles;

public record UserDetails(UUID id , String username, String password, List<UserRoles> roles) {

}
