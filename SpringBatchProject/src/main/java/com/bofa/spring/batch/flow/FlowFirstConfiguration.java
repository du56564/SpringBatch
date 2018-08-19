package com.bofa.spring.batch.flow;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class FlowFirstConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step myStep() {
		return stepBuilderFactory.get("myStep")
				.tasklet((contribution, ChunkContext)->{
					System.out.println("myStep executed.");
					return RepeatStatus.FINISHED;
				}).build();
	}

	@Bean
	public Job flowFirstJob(Flow flow) {
		return jobBuilderFactory.get("flowFirstJob")
				.start(flow)
				.next(myStep())
				.end()
				.build();
	}
}
