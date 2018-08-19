package com.bofa.spring.batch.itemreader.flatfiles.multipleresources;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;

public class CustomItemWriter {

	@Bean
	public ItemWriter<Customer> cutomItemWriter(){
		return items->{
			for (Customer customer : items) {
				System.out.println(customer.toString());
			}
		};
	}
	
}