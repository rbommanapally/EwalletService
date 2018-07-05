package com.ewallet;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.ewallet")
@EnableAutoConfiguration
@SpringBootApplication
public class EwalletApiApplication {

	// public static void main(String[] args) {
	// // SpringApplication.run(ewalletApiApplication.class, args);
	// }

}
