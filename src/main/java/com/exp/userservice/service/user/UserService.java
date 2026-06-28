package com.exp.userservice.service.user;

import java.time.Clock;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exp.userservice.dto.UserRequest;
import com.exp.userservice.dto.UserResponse;
import com.exp.userservice.entity.Role;
import com.exp.userservice.entity.User;
import com.exp.userservice.entity.UserInfo;
import com.exp.userservice.enums.UserRoles;
import com.exp.userservice.enums.UserStatus;
import com.exp.userservice.mapper.CustomMapper;
import com.exp.userservice.repository.UserRepository;

@Service
public class UserService {


	private UserRepository userRepository;
	private CustomMapper mapper;

	public UserService(UserRepository userRepository, CustomMapper mapper) {
		this.userRepository = userRepository;
		this.mapper = mapper;

	}

	@Transactional
	public UserResponse createUser(UserRequest userReq) {

		User user = mapper.toUserEntity(userReq);
		user.setUserInfo(setUserInfo(userReq));
		Role r = Role.builder().roles(UserRoles.ROLE_USER).build();
		user.addRole(r);
		User result = userRepository.saveAndFlush(user);

		return new UserResponse(result.getEmailId(), result.getUserInfo().getFirstName(),
				result.getUserInfo().getLastName(), result.getUserInfo().getGender(),
				result.getUserInfo().getMobileNo(), result.getUserInfo().getTimezone());

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
