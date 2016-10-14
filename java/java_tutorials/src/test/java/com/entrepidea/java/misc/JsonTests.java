package com.entrepidea.java.misc;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTests {

	private ObjectMapper objMapper;
	private byte[] buffer;
	
	@Before
	public void setUp() throws Exception {
		objMapper = new ObjectMapper();
		File f = new File("");
		FileInputStream fis = new FileInputStream(f);
		fis.read(buffer);
		fis.close();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
