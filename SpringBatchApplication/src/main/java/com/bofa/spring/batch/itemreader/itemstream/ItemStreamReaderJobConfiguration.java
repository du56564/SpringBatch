package com.bofa.spring.batch.itemreader.itemstream;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemStreamReaderJobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	@StepScope
	public StatefulItemReader itemReader() {
		List<String> items = new ArrayList<>();
		
		for(int i=0; i<=100; i++) {
			items.add(String.valueOf(i));
		}
		return new StatefulItemReader(items);
	}
	@Bean
	public ItemWriter<String> itemWriter() {
		return new ItemWriter<String>() {
			@Override
			public void write(List<? extends String> items) throws Exception {
				for(String item: items) {
					System.out.println(">>"+item);
				}
			}
		};
	}
	
	@Bean
	public Step stepItemStream() {
		return stepBuilderFactory.get("stepItemStream")
				.<String,String>chunk(10)
				.reader(itemReader())
				.writer(itemWriter())
				.build();
	}
	
	@Bean
	public Job jobItemStream() {
		return jobBuilderFactory.get("jobItemStream")
				.start(stepItemStream())
				.build();
	}
	
}
