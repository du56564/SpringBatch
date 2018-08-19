package com.bofa.spring.batch.flow.decider;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeciderConfguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step initStep() {
		return stepBuilderFactory.get("initStep")
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						System.out.println("Step is being initialzed.");
						return RepeatStatus.FINISHED;
					}
				}).build();
		
	}
	
	@Bean 
	public Step evenStep() {
		return stepBuilderFactory.get("evenStep")
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						System.out.println("Step is even.");
						return RepeatStatus.FINISHED;
					}
				}).build();
		
	} 
	
	@Bean
	public Step oddStep() {
		return stepBuilderFactory.get("oddStep")
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
						System.out.println("Step is Odd.");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Job jobDecider() {
		return jobBuilderFactory.get("jobDecider")
				.start(initStep())
				.next(decider())
				.from(decider()).on("ODD").to(oddStep())
				.from(decider()).on("EVEN").to(evenStep())
				.from(oddStep()).on("*").to(decider())
				.from(decider()).on("ODD").to(oddStep())
				.from(decider()).on("EVEN").to(evenStep())
				.end()
				.build();
	}
	
	@Bean
	public JobExecutionDecider decider() {
		return new EvenOddDecider();
	}
	
	
	
	public static class EvenOddDecider implements JobExecutionDecider{
		int count=0;
		@Override
		public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
			count++;
			if(count%2==0) return new FlowExecutionStatus("EVEN");
			else return new FlowExecutionStatus("ODD");
		}
		
	}

}

