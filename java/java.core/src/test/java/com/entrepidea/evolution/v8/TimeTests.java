package com.entrepidea.evolution.v8;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

/*
 * New Java Time class eliminate the thread-safety issues of previous java.util.Date, DateFormatter, and more.
 * 
 * http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html
 *
 * Since JDK 8
 * 
 * */
public class TimeTests {

	@Test
	public void test() {
		LocalDateTime timePoint = LocalDateTime.now();     // The current date and time
			LocalDate.of(2012, Month.DECEMBER, 12); // from values
			LocalDate.ofEpochDay(150);  // middle of 1970
			LocalTime.of(17, 18); // the train I took home today
			LocalTime.parse("10:15:30"); // From a String
			System.out.println(timePoint);
			
			LocalDate theDate = timePoint.toLocalDate();
			Month month = timePoint.getMonth();
			int day = timePoint.getDayOfMonth();
			timePoint.getSecond();
			
			System.out.println(theDate+","+month+", "+day);

	}

}
