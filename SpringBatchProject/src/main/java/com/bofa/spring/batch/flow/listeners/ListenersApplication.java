package com.bofa.spring.batch.flow.listeners;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan("com.bofa.spring.batch.flow.listeners")
public class ListenersApplication {
public static void main(String[] args) throws Exception {
	SpringApplication.run(ListenersApplication.class, args);
}

}
