package com.robotbot.finance_tracker_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinanceTrackerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceTrackerServerApplication.class, args);
	}

}
