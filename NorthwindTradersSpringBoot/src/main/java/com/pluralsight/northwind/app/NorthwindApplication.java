package com.pluralsight.northwind.app;

import com.pluralsight.northwind.service.DatabaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.pluralsight.northwind.service.UserInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.pluralsight.northwind")
public class NorthwindApplication implements CommandLineRunner {
	@Autowired
	UserInterface ui;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	DatabaseConfiguration dbconfig;

	public static void main(String[] args) {
		SpringApplication.run(NorthwindApplication.class, args);
	}

	@Override
	public void run(String... args) {
		ui.showHomeScreen();
		dbconfig.dataSource();

		//To manually close any lingering threads from Spring Boot app, second arg is a lambda to provide exit code 0
		SpringApplication.exit(applicationContext, () -> 0);
	}
}
