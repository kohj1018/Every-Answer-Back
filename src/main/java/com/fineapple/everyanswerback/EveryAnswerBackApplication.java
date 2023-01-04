package com.fineapple.everyanswerback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EveryAnswerBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(EveryAnswerBackApplication.class, args);
	}

}
