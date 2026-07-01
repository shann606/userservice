package com.exp.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.exp.userservice")
@EnableTransactionManagement
public class AppConfig {
	
	@Bean PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
