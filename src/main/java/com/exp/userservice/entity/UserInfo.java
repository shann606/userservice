package com.exp.userservice.entity;

import java.time.OffsetDateTime;

import com.exp.userservice.enums.Gender;
import com.exp.userservice.enums.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

	@Column(name = "exp_firstname")
	private String firstName;
	@Column(name = "exp_lastname")
	private String lastName;
	@Enumerated(EnumType.STRING)
	@Column(name = "exp_gender", nullable = false)
	private Gender gender;
	@Enumerated(EnumType.STRING)
	@Column(name = "exp_status", nullable = false)
	private UserStatus status;

	@Column(name = "exp_mobile_no")
	private String mobileNo;
	@Column(name = "exp_created_on")
	private OffsetDateTime createdOn;
	@Column(name = "exp_updated_on")
	private OffsetDateTime updatedOn;
	@Column(name = "exp_created_by")
	private String createdby;
	@Column(name = "exp_updated_by")
	private String updatedby;
	@Column(name = "exp_last_logged")
	private OffsetDateTime lastLoggedOn;
	@Column(name = "exp_timezone")
	private String timezone;

}
