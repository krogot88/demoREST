package ru.krogot88.demorest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemorestApplication {



	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("1234"));

		SpringApplication.run(DemorestApplication.class, args);
	}

}
