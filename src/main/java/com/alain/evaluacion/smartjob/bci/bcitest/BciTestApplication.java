package com.alain.evaluacion.smartjob.bci.bcitest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;



@SpringBootApplication
@PropertySource("classpath:messages.properties")
public class BciTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BciTestApplication.class, args);
	}


}
