package com.bofa.spring.batch.itemreader.xmlreader;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;

public class CustomItemWriter {

	public CustomItemWriter() {

	}
	
	@Bean
	public ItemWriter<Customer> customItemWriter(){
		return items->{
			for (Customer customer : items) {
				System.out.println(customer.toString());
			}
		};
	}
	
}