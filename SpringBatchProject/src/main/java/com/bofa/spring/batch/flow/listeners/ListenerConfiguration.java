package com.bofa.spring.batch.flow.listeners;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class ListenerConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public ItemReader<String> reader() {
		return new ListItemReader<>(Arrays.asList("One", "two", "three"));
	}
	
	@Bean
	public ItemWriter<String> writer(){
		return new ItemWriter<String>() {
			@Override
			public void write(List<? extends String> items) throws Exception {
				for(String item : items) {
					System.out.println("Writing item:" + item);
				}	
			}
		};
	}
	
/*	@Bean
	@StepScope
	public Tasklet updateJobTasklet(@Value("#{jobParameters['message']}") String message) {
		return new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("New Taks:"+ message);
				return RepeatStatus.FINISHED;
			}
		};
	}
*/	
	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2")
				.<String,String>chunk(2)
				.faultTolerant()
				.listener(new ChunkListenerMsg())
				.reader(reader())
				.writer(writer())
				.build();
	}
	
	@Bean
	public Job listenerJob2(JavaMailSender mailSender) {
		return jobBuilderFactory.get("listenerJob2")
				.start(step2())
				.listener(new JobListener(mailSender))
				.build();
	}
}