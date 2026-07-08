package com.exp.userservice.controller.user;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exp.userservice.dto.UserDetails;
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

	@GetMapping("/{emailId}")
	ResponseEntity<UserDetails> authenticate(@PathVariable(name = "emailId", required = true) String emailId)
			throws Exception {
		log.info("authentication call from the user " + emailId);
		UserDetails details = userService.authenticate(emailId);
		userService.updateUserLoggedOn(details.id());
		return new ResponseEntity<UserDetails>(details, HttpStatus.OK);

	}

	@PutMapping("/{id}")
	ResponseEntity<?> updateUser(@PathVariable(name = "id", required = true) UUID id)  {

		return null;

	}

	@PatchMapping("/{id}/deactivate")
	ResponseEntity<?> deActivateUser(@PathVariable(name = "id", required = true) UUID id)  {

		return null;

	}

}
