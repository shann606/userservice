package com.exp.userservice.service.user;

import java.time.Clock;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.exp.userservice.dto.UserDetails;
import com.exp.userservice.dto.UserFullDetailsResponse;
import com.exp.userservice.dto.UserRequest;
import com.exp.userservice.dto.UserResponse;
import com.exp.userservice.dto.UserSearchResponse;
import com.exp.userservice.dto.UserUpateRequest;
import com.exp.userservice.dto.UserUpateResponse;
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
	public UserDetails authenticate(String emailId) throws UserNotFoundException {

		List<User> result1 = userRepository.findByEmailId(emailId);
		if (result1.isEmpty()) {
			throw new UserNotFoundException("User not exits in the system");
		}

		User user = userRepository.checkForAuthenticate(emailId, UserStatus.ACTIVE);

		return new UserDetails(user.getId(), user.getEmailId(), user.getPassword(), user.getRoles().stream()

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

	@Transactional(readOnly = true)
	public Page<UserSearchResponse> searchUser(String username, String firstname, String lastname, String createdFrom,
			String createdto, int pageNo) {
		OffsetDateTime createdF = null, createdT = null;

		if (!createdFrom.equals("")) {
			createdF = dateConverter(createdFrom);
		}

		if (!createdto.equals("")) {
			createdT = dateConverter(createdto);
		}

		return userRepository.searchUser(username, firstname, lastname, createdF, createdT, PageRequest.of(pageNo, 2));

	}

	private OffsetDateTime dateConverter(String date) {

		LocalDate localDate = LocalDate.parse(date);

		ZoneId dbZone = ZoneId.of("Asia/Kolkata");
		ZoneOffset dbOffset = dbZone.getRules().getOffset(localDate.atStartOfDay());

		return localDate.atStartOfDay().atOffset(dbOffset);

	}

	@Transactional
	public void updateUserLoggedOn(UUID id) {
		userRepository.updateUserLastLoggedin(id, OffsetDateTime.now(ZoneOffset.UTC));

	}

	@Transactional(readOnly = true)
	public UserFullDetailsResponse getUserFullDetails(UUID id) throws UserNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("user not found in the system"));

		return new UserFullDetailsResponse(id, user.getEmailId(), user.getUserInfo().getFirstName(),
				user.getUserInfo().getLastName(), user.getUserInfo().getMobileNo(),
				user.getUserInfo().getStatus().toString(), getAssignedRoles(user.getRoles()), getUserStatus(),
				getUserRoles(), user.getUserInfo().getTimezone());

	}

	@Transactional
	public UserUpateResponse updateUser(UUID id, UserUpateRequest req) throws UserNotFoundException {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found in the downstream"));

		if (StringUtils.hasText(req.password()) && !req.password().equals("undefined")) {

			user.setPassword(encoder.encode(req.password()));
		}

		if (StringUtils.hasText(req.mobileno())) {

			user.getUserInfo().setMobileNo(req.mobileno());

		}

		if (StringUtils.hasText(req.statusupdate())) {
			user.getUserInfo().setStatus(UserStatus.valueOf(req.statusupdate()));
		}

		if (StringUtils.hasText(req.addrole())) {

			user.addRole(Role.builder().roles(UserRoles.valueOf(req.addrole())).build());
		}

		if (StringUtils.hasText(req.deleteroles())) {

			user.getRoles().stream().filter(x -> x.getRoles().toString().equals(req.deleteroles())).findFirst()
					.ifPresent(r -> user.removeRole(r)

					);

		}
		if (StringUtils.hasText(req.timezone())) {
			user.getUserInfo().setTimezone(req.timezone());
		}

		User result = userRepository.saveAndFlush(user);

		return new UserUpateResponse(result.getId(), result.getEmailId(), result.getPassword(),
				result.getUserInfo().getFirstName(), result.getUserInfo().getLastName(),
				result.getUserInfo().getMobileNo(), result.getUserInfo().getStatus().toString(),
				getAssignedRoles(result.getRoles()), getUserStatus(), getUserRoles(),
				result.getUserInfo().getTimezone());
	}

	private List<String> getUserStatus() {

		return Stream.of(UserStatus.values()).map(Enum::name).toList();

	}

	private List<String> getUserRoles() {
		return Stream.of(UserRoles.values()).map(Enum::name).toList();

	}

	private Set<String> getAssignedRoles(Set<Role> roles) {

		return roles.stream().map(x -> x.getRoles().toString()).collect(Collectors.toSet());
	}
}
