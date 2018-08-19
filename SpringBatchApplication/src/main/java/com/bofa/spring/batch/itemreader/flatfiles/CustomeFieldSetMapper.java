package com.bofa.spring.batch.itemreader.flatfiles;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CustomeFieldSetMapper implements FieldSetMapper<Customer> {

	@Override
	public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
		return new Customer(fieldSet.readLong("id"),fieldSet.readString("firstName")
				,fieldSet.readString("lastName"),fieldSet.readDate("birthDate","yyyy-MM-dd HH:mm:ss"));
	}

}
