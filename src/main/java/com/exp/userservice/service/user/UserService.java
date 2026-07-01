package com.exp.userservice.service.user;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exp.userservice.dto.UserDetails;
import com.exp.userservice.dto.UserRequest;
import com.exp.userservice.dto.UserResponse;
import com.exp.userservice.entity.Role;
import com.exp.userservice.entity.User;
import com.exp.userservice.entity.UserInfo;
import com.exp.userservice.enums.UserRoles;
import com.exp.userservice.enums.UserStatus;
import com.exp.userservice.exception.UserAlreadyFoundException;
import com.exp.userservice.exception.UserNotFoundException;
import com.exp.userservice.mapper.CustomMapper;
import com.exp.userservice.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private CustomMapper mapper;
	private PasswordEncoder encoder;

	public UserService(UserRepository userRepository, CustomMapper mapper, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.mapper = mapper;
		this.encoder = encoder;

	}

	@Transactional
	public UserResponse createUser(UserRequest userReq) throws UserAlreadyFoundException {

		List<User> result1 = userRepository.findByEmailId(userReq.emailId());
		if (!result1.isEmpty()) {
			throw new UserAlreadyFoundException("User already present in the system please try with other email id");
		}

		User user = mapper.toUserEntity(userReq);
		user.setPassword(encoder.encode(user.getPassword()));
		user.setUserInfo(setUserInfo(userReq));
		Role r = Role.builder().roles(UserRoles.ROLE_USER).build();
		user.addRole(r);
		User result = userRepository.saveAndFlush(user);

		return new UserResponse(result.getEmailId(), result.getUserInfo().getFirstName(),
				result.getUserInfo().getLastName(), result.getUserInfo().getGender(),
				result.getUserInfo().getMobileNo(), result.getUserInfo().getTimezone());

	}

	@Transactional(readOnly = true)
	public UserDetails authenticate(String emailId) throws  UserNotFoundException {

		List<User> result1 = userRepository.findByEmailId(emailId);
		if (result1.isEmpty()) {
			throw new UserNotFoundException("User not exits in the system");
		}

		User user = userRepository.checkForAuthenticate(emailId);

		return new UserDetails(user.getEmailId(), user.getPassword(), user.getRoles().stream()

				.map(Role::getRoles).distinct().toList());

	}

	private UserInfo setUserInfo(UserRequest userReq) {

		UserInfo info = new UserInfo();
		info.setFirstName(userReq.firstName());
		info.setLastName(userReq.lastName());

		info.setMobileNo(userReq.mobileNo());
		info.setTimezone(userReq.timezone());
		info.setGender(userReq.gender());
		info.setStatus(UserStatus.ACTIVE);
		info.setCreatedby("admin");
		info.setCreatedOn(OffsetDateTime.now(Clock.systemUTC()));

		return info;

	}

}
