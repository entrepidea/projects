package com.entrepidea.java.v8.concurrent;

import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Test;

/**
 * CompletableFuture and its interface CompletableStage were introduced into Java's concurrent package since JDK 1.8.
 * It is used to facilitate asynchronous programming. See the link(s) for more.
 * {@link https://community.oracle.com/docs/DOC-995305} 
 * 
 * */
public class CompletableFutureTests {
	@Test
	public void completedTest(){
		CompletableFuture<String> cf = CompletableFuture.completedFuture("I am done");
		Assert.assertTrue(cf.isDone());
		Assert.assertEquals("I am done", cf.join());
	}
}
