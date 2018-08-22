package com.bofa.spring.batch.itemwriter.flatfiles.multipleresources;

import java.io.File;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class CustomFileFlatItemWriter {

	public CustomFileFlatItemWriter() {
	}
	

	@Bean
	public FlatFileItemWriter<Customer> jsonItemWriter() throws Exception{
		FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<>();
		writer.setLineAggregator(new CustomerLineAggregator());
		String customAggregatorPath = File.createTempFile("customCustomerAggregateOutput", ".json").getAbsolutePath();
		System.out.println("OUTPUT PATH : "+customAggregatorPath);
		writer.setResource(new FileSystemResource(customAggregatorPath));
		writer.afterPropertiesSet();	
		return writer;
	}
	
	
	
}
