package com.bofa.spring.batch.flow.listeners;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class JobListener implements JobExecutionListener{

	private JavaMailSender mailSender;
	
	public JobListener(JavaMailSender mailSender) {
		this.mailSender=mailSender;
	}
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		String jobName=jobExecution.getJobInstance().getJobName();
		SimpleMailMessage mail = getSimpleMailMessage(String.format("%s is starting", jobName),String.format("As Per request we are in"
				+ "informing you that %s is starting", jobName));
		
		mailSender.send(mail);
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		String jobName=jobExecution.getJobInstance().getJobName();
		SimpleMailMessage mail = getSimpleMailMessage(String.format("%s has completed", jobName),String.format("As Per request we are in"
				+ "informing you that %s has completed", jobName)); 
		mailSender.send(mail);
	}

	private SimpleMailMessage getSimpleMailMessage(String subject, String body) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("du56564@gmail.com");
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		return mailMessage;
	}
	
	
}
