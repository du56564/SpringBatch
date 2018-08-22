package com.bofa.spring.batch.itemwriter.flatfiles;

import java.io.File;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

public class CustomFileFlatItemWriter {

	public CustomFileFlatItemWriter() {
	}
	
	@Bean
	public FlatFileItemWriter<Customer> flatFileItemWriterFromDB() throws Exception{
		FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<>();
		writer.setLineAggregator(new PassThroughLineAggregator<>());
		String customAggregatorPath = File.createTempFile("CustomerOutput", ".out").getAbsolutePath();
		System.out.println("OUTPUT PATH : "+customAggregatorPath);
		writer.setResource(new FileSystemResource(customAggregatorPath));
		writer.afterPropertiesSet();	
		return writer;
	}

	@Bean
	public FlatFileItemWriter<Customer> flatFileItemWriterFromDBCustomAggregator() throws Exception{
		FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<>();
		writer.setLineAggregator(new CustomerLineAggregator());
		String customAggregatorPath = File.createTempFile("customCustomerAggregateOutput", ".json").getAbsolutePath();
		System.out.println("OUTPUT PATH : "+customAggregatorPath);
		writer.setResource(new FileSystemResource(customAggregatorPath));
		writer.afterPropertiesSet();	
		return writer;
	}
	
	
	
}
