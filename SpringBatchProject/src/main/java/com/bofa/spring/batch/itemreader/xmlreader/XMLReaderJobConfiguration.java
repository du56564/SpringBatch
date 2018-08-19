package com.bofa.spring.batch.itemreader.xmlreader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class XMLReaderJobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step customXMLItemReaderStep() {
		return stepBuilderFactory.get("customXMLItemReaderStep")
				.<Customer, Customer>chunk(10)
				.reader(new CustomXMLItemReader().customXMLItemReader())
				.writer(new CustomItemWriter().customItemWriter())
				.build();
	}
	@Bean
	public Job jobXMLItemReader() {
		return jobBuilderFactory.get("jobXMLItemReader")
				.start(customXMLItemReaderStep())
				.build();
	}

	
	
}
