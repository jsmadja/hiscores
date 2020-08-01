package com.shmup.hiscores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HiscoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiscoresApplication.class, args);
	}

}
