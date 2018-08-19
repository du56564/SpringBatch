package com.bofa.spring.batch.itemreader.flatfiles.multipleresources;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;



@Configuration
public class MultiResourceFlatFileItemReaderJobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Value("classpath*:/data/customer*.csv")
	private Resource[] inputFiles;
	
	@Bean
	public Step customMultiResourceFlatFileItemReaderStep() {
		return stepBuilderFactory.get("customMultiResourceFlatFileItemReaderStep")
				.<Customer, Customer>chunk(10)
				.reader(new CustomMultipleResourceFileFlatItemReader(inputFiles).multiResourceItemReader())
				.writer(new CustomItemWriter().cutomItemWriter())
				.build();
	}
	@Bean
	public Job jobMultiResourceFlatFileItemReader1() {
		return jobBuilderFactory.get("jobMultiResourceFlatFileItemReader1")
				.start(customMultiResourceFlatFileItemReaderStep())
				.build();
	}

	
	
}
