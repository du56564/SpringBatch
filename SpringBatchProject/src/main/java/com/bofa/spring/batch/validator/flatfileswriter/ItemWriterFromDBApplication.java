package com.bofa.spring.batch.validator.flatfileswriter;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan("com.bofa.spring.batch.validator.flatfileswriter")
public class ItemWriterFromDBApplication {
public static void main(String[] args) throws Exception {
	SpringApplication.run(ItemWriterFromDBApplication.class, args);
}

}
