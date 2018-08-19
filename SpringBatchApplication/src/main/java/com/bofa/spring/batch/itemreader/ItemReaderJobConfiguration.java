package com.bofa.spring.batch.itemreader;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemReaderJobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public StatelessItemReader statelessItemReader() {
		List<String> data = Arrays.asList("Deepak", "Ravi", "Khushi");
		return new StatelessItemReader(data);
	}
	
	@Bean
	public Step stepItemReader1() {
		return stepBuilderFactory.get("stepItemReader1")
				.<String,String>chunk(3)
				.reader(statelessItemReader())
				.writer(listItem->{
					for(String item: listItem) {
						System.out.println("CurrentItem: "+ item);
					}
				})
				.build();
	}
	@Bean
	public Job jobItemReader1() {
		return jobBuilderFactory.get("jobItemReader1")
				.start(stepItemReader1())
				.build();
	}
}
