package com.bofa.spring.batch.itemreader.xmlreader;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan("com.bofa.spring.batch.itemreader.xmlreader")
public class XMLItemReaderApplication {
public static void main(String[] args) throws Exception {
	SpringApplication.run(XMLItemReaderApplication.class, args);
}

}
