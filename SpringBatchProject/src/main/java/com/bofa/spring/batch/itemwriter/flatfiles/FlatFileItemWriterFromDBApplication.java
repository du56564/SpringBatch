package com.bofa.spring.batch.itemwriter.flatfiles;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan("com.bofa.spring.batch.itemwriter.flatfiles")
public class FlatFileItemWriterFromDBApplication {
public static void main(String[] args) throws Exception {
	SpringApplication.run(FlatFileItemWriterFromDBApplication.class, args);
}

}
