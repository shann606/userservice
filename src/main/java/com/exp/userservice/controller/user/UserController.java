package com.exp.userservice.controller.user;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exp.userservice.dto.UserRequest;
import com.exp.userservice.dto.UserResponse;
import com.exp.userservice.service.user.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user) throws Exception {

		log.info("Getting user info" + user.toString());

		UserResponse response = userService.createUser(user);
		log.info("Responding user info" + response.toString());

		return new ResponseEntity<UserResponse>(response, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	ResponseEntity<?> updateUser(@PathVariable(name = "id", required = true) UUID id) throws Exception {

		return null;

	}

	@PatchMapping("/{id}/deactivate")
	ResponseEntity<?> deActivateUser(@PathVariable(name = "id", required = true) UUID id) throws Exception {

		return null;

	}

}
