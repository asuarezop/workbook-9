package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.UserInterface;

@SpringBootApplication
public class NorthwindTradersSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(NorthwindTradersSpringBootApplication.class, args);
		UserInterface ui = new UserInterface();

		ui.showHomeScreen();
	}

}
