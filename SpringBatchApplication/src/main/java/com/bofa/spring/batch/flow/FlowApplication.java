package com.bofa.spring.batch.flow;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan("com.bofa.spring.batch.flow.config")
public class FlowApplication {
public static void main(String[] args) throws Exception {
	SpringApplication.run(FlowApplication.class, args);
}


}
