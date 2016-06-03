package com.entrepidea.java.enums;

/**
 * @description: this is about the new enum type since JDK 1.5. The new enum is a class that exports one instance 
 * for each enumeration constant via a final static final field.
 * @note: the follow demoed tactics is called constant-specific method implementation. 
 * @see: item #30, EJ, "Use enum instead of int constants" 
 * */
import org.junit.Test;

import junit.framework.TestCase;

public class EnumOperationTest extends TestCase{
	public enum Operation {
	PLUS{
		@Override
		public double apply(double x, double y) {
			return x + y;
		}},
	MINUS{
			@Override
			public double apply(double x, double y) {
				return x - y;
			}			
		},
	MULTIPLE{
			@Override
			public double apply(double x, double y) {
				return x*y;
			}			
		},
	DIVIDE{
			@Override
			public double apply(double x, double y) {
				return x/y;
			}			
		};
	
	abstract public double apply(double x, double y);
	}
	
	
	@Test
	public void testEnumOperation(){
		double res = Operation.PLUS.apply(12, 15);
		assertTrue(res==27);
		
		res = Operation.MINUS.apply(15, 4);
		assertTrue(res==11);
		
	}
}



