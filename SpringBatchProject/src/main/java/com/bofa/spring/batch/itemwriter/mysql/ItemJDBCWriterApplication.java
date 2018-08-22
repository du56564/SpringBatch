package com.bofa.spring.batch.itemwriter.mysql;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class ItemJDBCWriterApplication {
public static void main(String[] args) throws Exception {
	SpringApplication.run(ItemJDBCWriterApplication.class, args);
}

}
