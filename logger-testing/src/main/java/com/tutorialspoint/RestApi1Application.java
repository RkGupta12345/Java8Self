package com.tutorialspoint;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApi1Application {

	public static void main(String[] args) {
		SpringApplication.run(RestApi1Application.class, args);
		final Logger logger=Logger.getLogger(RestApi1Application.class);
		logger.info("Hello this is an info message");
	}

}
