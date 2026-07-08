package com.exp.userservice.dto;

import java.time.OffsetDateTime;

public record UserSearchRequest(String username, String firstname, String lastname, OffsetDateTime createdFrom,
		OffsetDateTime createdto) {

}
