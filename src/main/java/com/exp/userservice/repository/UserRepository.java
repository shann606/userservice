package com.exp.userservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exp.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	List<User> findByEmailId(String emailId);

	
	
	@Query("""
		    SELECT DISTINCT u
		    FROM User u
		    LEFT JOIN FETCH u.roles
		    WHERE u.emailId = ?1
		""")
	User checkForAuthenticate(String emailId);

}
