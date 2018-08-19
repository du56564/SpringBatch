package com.bofa.spring.batch.flow.split;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan("com.bofa.spring.batch.flow.split")
public class SplitApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SplitApplication.class, args);
	}

}
