package com.personal.musicplayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.personal")
@EntityScan("com.personal.entity")
@EnableJpaRepositories("com.personal.repository")
@EnableScheduling
public class MusicplayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicplayerApplication.class, args);
	}

}
