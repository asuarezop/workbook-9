package com.pluralsight.northwind.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.pluralsight.northwind.service.UserInterface;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.pluralsight.northwind")
public class NorthwindTradersSpringBootApplication implements CommandLineRunner {
	@Autowired
	UserInterface ui;

	public static void main(String[] args) {
		SpringApplication.run(NorthwindTradersSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ui.showHomeScreen();
	}
}
