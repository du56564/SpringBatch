package com.bofa.spring.batch.itemreader.itemstream;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan("com.bofa.spring.batch.itemreader.itemstream")
public class ItemStreamReaderApplication {
public static void main(String[] args) throws Exception {
	SpringApplication.run(ItemStreamReaderApplication.class, args);
}
}
