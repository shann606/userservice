package com.exp.userservice.controller.admin;

import com.exp.userservice.dto.UserUpateRequest;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exp.userservice.dto.UserFullDetailsResponse;
import com.exp.userservice.dto.UserSearchResponse;
import com.exp.userservice.dto.UserUpateResponse;
import com.exp.userservice.exception.UserNotFoundException;
import com.exp.userservice.service.user.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/admin")
@Slf4j
public class AdminController {

	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;

	}

	@GetMapping("/usersearch")
	ResponseEntity<Page<UserSearchResponse>> userSearch(@RequestParam(required = false) String username,
			@RequestParam(required = false) String firstname, @RequestParam(required = false) String lastname,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate,
			@RequestParam int pageNo) {

		log.info("Getting the from date and to date ::" + fromdate + "----------" + todate);

		return new ResponseEntity<Page<UserSearchResponse>>(
				userService.searchUser(username, firstname, lastname, fromdate, todate, pageNo), HttpStatus.OK);

	}

	@GetMapping("/usersearch/{id}")
	ResponseEntity<UserFullDetailsResponse> userDetails(@PathVariable UUID id) throws UserNotFoundException {
		return new ResponseEntity<UserFullDetailsResponse>(userService.getUserFullDetails(id), HttpStatus.OK);

	}

	@PatchMapping("/users/{id}")
	ResponseEntity<UserUpateResponse> updateUser(@PathVariable UUID id, @RequestBody UserUpateRequest req, @RequestHeader(name = "X-Username") String updatedBy)
			throws UserNotFoundException {
		log.info("Are we getting the data " + req.toString());

		return new ResponseEntity<UserUpateResponse>(userService.updateUser(id, req , updatedBy), HttpStatus.OK);
	}

}
