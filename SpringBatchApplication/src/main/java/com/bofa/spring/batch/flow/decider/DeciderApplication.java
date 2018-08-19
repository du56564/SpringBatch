package com.bofa.spring.batch.flow.decider;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan("com.bofa.spring.batch.flow.decider")
public class DeciderApplication {
public static void main(String[] args) throws Exception {
	SpringApplication.run(DeciderApplication.class, args);
}

}