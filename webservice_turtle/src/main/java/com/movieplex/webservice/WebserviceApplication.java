package com.movieplex.webservice;

import com.movieplex.webservice.services.FusekiServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.util.Collections;

@SpringBootApplication
public class WebserviceApplication  {

	private static FusekiServiceImpl fusekiService = new FusekiServiceImpl();

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication app = new SpringApplication(WebserviceApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "8082"));
		app.run(args);
		fusekiService.checkFusekiServer();
	}

}
