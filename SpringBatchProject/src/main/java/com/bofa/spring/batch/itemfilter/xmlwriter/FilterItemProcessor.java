package com.bofa.spring.batch.itemfilter.xmlwriter;

import org.springframework.batch.item.ItemProcessor;

public class FilterItemProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer item) throws Exception {
		if(item.getId()%2==0) {
			return new Customer(item.getId(), item.getFirstName().toUpperCase()
					, item.getLastName().toUpperCase(), item.getBirthdate());
		}else {
			return null;
		}
	}
}
