package com.cineplexx.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class WebserviceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WebserviceApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "8083"));
		app.run(args);
	}

}
