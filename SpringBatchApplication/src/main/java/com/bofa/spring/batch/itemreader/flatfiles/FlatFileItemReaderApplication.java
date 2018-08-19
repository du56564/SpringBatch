package com.bofa.spring.batch.itemreader.flatfiles;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan("com.bofa.spring.batch.itemreader.flatfiles")
public class FlatFileItemReaderApplication {
public static void main(String[] args) throws Exception {
	SpringApplication.run(FlatFileItemReaderApplication.class, args);
}

}
