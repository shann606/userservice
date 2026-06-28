package com.exp.userservice.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = "com.exp.userservice")
@EnableTransactionManagement
public class AppConfig {

}
