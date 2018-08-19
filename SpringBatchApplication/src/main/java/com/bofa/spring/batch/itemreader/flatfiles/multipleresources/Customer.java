package com.bofa.spring.batch.itemreader.flatfiles.multipleresources;

import java.util.Date;

import org.springframework.batch.item.ResourceAware;
import org.springframework.core.io.Resource;

public class Customer implements ResourceAware{

	private final long id;
	
	private final String firstName;
	
	private final String lastName;
	
	private final Date birthDate;
	
	private Resource resource;

	public Customer(long id, String firstName, String lastName, Date birthDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate
				+ "]";
	}

	@Override
	public void setResource(Resource resource) {
		this.resource=resource;
	}
	
	
	
	
	
	
}
