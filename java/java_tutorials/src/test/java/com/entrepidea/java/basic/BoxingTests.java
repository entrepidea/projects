package com.entrepidea.java.basic;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoxingTests {

	private static Logger log = LoggerFactory.getLogger(BoxingTests.class);
	private static double ONE_BILLION = 1000000000.0;
	
	@Test
	public void performanceTest() {
		Long sum = 0L;
		long start = System.nanoTime();
		log.info("Hold on...");
		for(long i=0;i<Integer.MAX_VALUE;i++){
			sum += i;
		}
		//see this link about convert nano seconds to second: http://stackoverflow.com/questions/924208/java-how-do-you-convert-nanoseconds-to-seconds-using-the-timeunit-class
		log.info("When \"Long\" is used, it's taken "+TimeUnit.NANOSECONDS.toSeconds((System.nanoTime()-start))+" secs, the result is: "+sum);
		
		long sum2 = 0L;
		start = System.nanoTime();
		log.info("Hold on...");
		for(long i=0;i<Integer.MAX_VALUE;i++){
			sum2 += i;
		}
		log.info("When \"long\" is used, it's taken "+(double)((System.nanoTime()-start)/ONE_BILLION)+" secs, the result is: "+sum2);

	}

}
