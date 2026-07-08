package com.exp.userservice.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.exp.userservice.dto.UserSearchResponse;
import com.exp.userservice.entity.Role;
import com.exp.userservice.entity.User;
import com.exp.userservice.enums.UserRoles;
import com.exp.userservice.enums.UserStatus;

public interface UserRepository extends JpaRepository<User, UUID> {

	List<User> findByEmailId(String emailId);

	@Query("""
			    SELECT DISTINCT u
			    FROM User u
			    LEFT JOIN FETCH u.roles
			    WHERE u.emailId = ?1
			    and u.userInfo.status=?2
			""")
	User checkForAuthenticate(String emailId, UserStatus status);

	@Query("""
			SELECT new com.exp.userservice.dto.UserSearchResponse(u.id,
			  u.emailId as username, u.userInfo.firstName as firstname , u.userInfo.lastName as lastname ,u.userInfo.createdOn as createdon,
			  u.userInfo.status as status
			)
			FROM User u
			WHERE
			    (?1 IS NULL OR LOWER(u.emailId) LIKE LOWER(CONCAT('%', ?1, '%')))
			AND (?2 IS NULL OR LOWER(u.userInfo.firstName) LIKE LOWER(CONCAT('%', ?2, '%')))
			AND (?3 IS NULL OR LOWER(u.userInfo.lastName) LIKE LOWER(CONCAT('%', ?3, '%')))
			AND (?4 IS NULL OR u.userInfo.createdOn >= ?4)
			AND (?5 IS NULL OR u.userInfo.createdOn <= ?5)
			""")
	Page<UserSearchResponse> searchUser(String username, String firstname, String lastname, OffsetDateTime createdFrom,
			OffsetDateTime createdto, Pageable pagable);

	@Modifying
	@Query("""
				update User u set u.userInfo.lastLoggedOn=?2
				where u.id=?1
			""")

	void updateUserLastLoggedin(UUID id, OffsetDateTime now);

}
