package smilyk.homeacc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import smilyk.homeacc.security.AppProperties;

@SpringBootApplication
public class HomeaccApplication {
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
//	@Bean
//	public SpringApplicationContext springApplicationContext() {
//		return new SpringApplicationContext();
//	}
//	@Bean(name = "AppProperties")
//	public AppProperties getAppProperties() {
//		return new AppProperties();
//	}

	public static void main(String[] args) {
		SpringApplication.run(HomeaccApplication.class, args);
	}

}
