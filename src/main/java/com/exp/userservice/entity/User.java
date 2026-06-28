package com.exp.userservice.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "exp_users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(name = "exp_emailid", nullable = false)
	private String emailId;
	@Column(name = "exp_password", nullable = false)
	private String password;

	@Embedded
	private UserInfo userInfo;

	@OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
	private final Set<Role> roles = new HashSet<Role>();

	/**
	 * Below Helper method is need to go with onetomany Bidirectional
	 */

	public void addRole(Role role) {
		roles.add(role);
		role.setUser(this);

	}

	public void removeRole(Role role) {
		roles.remove(role);
		role.setUser(null);

	}

}
