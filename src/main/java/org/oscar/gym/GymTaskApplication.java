package org.oscar.gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GymTaskApplication {

	public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("PASS123456"));
        SpringApplication.run(GymTaskApplication.class, args);
	}

}
