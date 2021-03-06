package com.bofa.spring.batch.flow.nestedjob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ParentNestedConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private Job childJob;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Bean
	public Step parentStep() {
		return stepBuilderFactory.get("parentStep")
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						System.out.println("This is parent step 1.");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Job parentJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		Step childJobStep = new JobStepBuilder(new StepBuilder("childJobStep"))
							.job(childJob)
							.launcher(jobLauncher)
							.repository(jobRepository)
							.transactionManager(transactionManager)
							.build();
		return jobBuilderFactory.get("parentJob")
				.start(parentStep())
				.next(childJobStep)
				.build();
	}

}
