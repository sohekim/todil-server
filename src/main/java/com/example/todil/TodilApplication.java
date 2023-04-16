package com.example.todil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(CORSConfig.class)
@SpringBootApplication
public class TodilApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodilApplication.class, args);
	}

}
