package com.mdrok._spring_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {

	private static final String selectedProfile = "test";

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class)
				.profiles(selectedProfile)
				.run(args);
	}
}
