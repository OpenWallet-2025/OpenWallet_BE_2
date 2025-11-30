package com.openwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OpenwalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenwalletApplication.class, args);
	}

}
