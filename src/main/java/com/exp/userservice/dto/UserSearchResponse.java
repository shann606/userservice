package com.exp.userservice.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.exp.userservice.enums.UserStatus;

public record UserSearchResponse(UUID id,String username, String firstname, String lastname ,OffsetDateTime createdon , UserStatus status ) {

}
