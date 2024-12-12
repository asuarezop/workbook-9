package com.pluralsight.dealership.app;

import com.pluralsight.dealership.services.DatabaseConfiguration;
import com.pluralsight.dealership.services.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.pluralsight.dealership")
public class DealershipApplication implements CommandLineRunner {
	@Autowired
	UserInterface ui;

	@Autowired
	DatabaseConfiguration dbConfig;

	@Autowired
	ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(DealershipApplication.class, args);
	}

	@Override
	public void run(String... args) {
		ui.showHomeScreen();
		dbConfig.dataSource();

		//To manually close any lingering threads from Spring Boot app, second arg is a lambda to provide exit code 0
		SpringApplication.exit(applicationContext, () -> 0);
	}
}
