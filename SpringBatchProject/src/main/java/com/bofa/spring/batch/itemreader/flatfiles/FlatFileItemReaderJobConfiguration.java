package com.bofa.spring.batch.itemreader.flatfiles;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class FlatFileItemReaderJobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step customFlatFileItemReaderStep() {
		return stepBuilderFactory.get("customFlatFileItemReaderStep")
				.<Customer, Customer>chunk(10)
				.reader(new CustomFileFlatItemReader().flatFileItemReader())
				.writer(new CustomItemWriter().cutomItemWriter())
				.build();
	}
	@Bean
	public Job jobFlatFileItemReader() {
		return jobBuilderFactory.get("jobFlatFileItemReader")
				.start(customFlatFileItemReaderStep())
				.build();
	}

	
	
}
