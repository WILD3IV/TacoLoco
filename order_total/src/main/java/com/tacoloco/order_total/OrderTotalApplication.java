package com.tacoloco.order_total;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Boot Application class - everything starts here.
 */
@SpringBootApplication
public class OrderTotalApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderTotalApplication.class, args);
	}

}
