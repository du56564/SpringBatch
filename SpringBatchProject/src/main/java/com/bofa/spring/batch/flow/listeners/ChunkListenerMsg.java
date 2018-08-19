package com.bofa.spring.batch.flow.listeners;


import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;

public class ChunkListenerMsg {
		
			@BeforeChunk
			public void beforeChunk() throws Exception {
				System.out.println(">> Before the chunk.");
			}
			
			@AfterChunk
			public void afterChunk() throws Exception {
				System.out.println(">> After the chunk.");
				
			}
	
	
}
