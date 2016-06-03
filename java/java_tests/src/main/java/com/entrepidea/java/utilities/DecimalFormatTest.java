package com.entrepidea.java.utilities;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DecimalFormatTest {

	/**
	 * @param args
	 */
	  public static String roundCurrency(String _inValue, String roundFormat){
			
		  if(roundFormat==null) return _inValue;
		  DecimalFormat formatter = null;
		  if(roundFormat.equalsIgnoreCase("ROUND 2")){
				formatter = new DecimalFormat("#,###.00");
		  }
		  else{ 
			  if(roundFormat!=null && roundFormat.equalsIgnoreCase("ROUND 3")){
				  formatter = new DecimalFormat("#,###.000");
			  }
			  else{
				  return _inValue;
			  }
		  }
		  double d = Double.parseDouble(_inValue);
		  _inValue = formatter.format(d);
		  return _inValue;
	  }
	  
	  public static String formatCurrency(String _inValue, String round, String formatType)
	  {
		  if(round==null) return _inValue;
		  DecimalFormat formatter = null;
		  if(round.equalsIgnoreCase("ROUND2")){
			  if(formatType != null && formatType.equals("999,999.00")){
				  formatter = (DecimalFormat)DecimalFormat.getCurrencyInstance();
				  formatter.applyPattern("#,###.00");
			  }
			  else{
				  if(formatType != null && formatType.equals("999.999,00")){
					  formatter = (DecimalFormat)DecimalFormat.getCurrencyInstance(Locale.GERMAN);
					  formatter.applyLocalizedPattern("#.###,00");
				  }
				  else{
					  return _inValue;
				  }
			  }
		  }
		  else{ 
			  if(round!=null && round.equalsIgnoreCase("ROUND3")){
				  if(formatType != null && formatType.equals("999,999.00")){
					  formatter = (DecimalFormat)DecimalFormat.getCurrencyInstance();
					  formatter.applyPattern("#,###.000");
				  }
				  else{
					  if(formatType != null && formatType.equals("999.999,00")){
						  formatter = (DecimalFormat)DecimalFormat.getCurrencyInstance(Locale.GERMAN);
						  formatter.applyLocalizedPattern("#.###,000");
					  }
					  else{
						  return _inValue;
					  }
				  }
			  }
			  else{
				  return _inValue;
			  }
		  }
		  double d = Double.parseDouble(_inValue);
		  _inValue = formatter.format(d);
		  return _inValue;
	  }
	  
	  public static String formatCurrency(String _inValue, String formatType)
	  {
		  double d = 0.0d;
	    NumberFormat nf = null;
	    if(formatType != null && formatType.equals("999,999.00")){
	    	nf = NumberFormat.getCurrencyInstance();
	    	try{
	    	  	  d = Double.parseDouble(_inValue);
	    	  	  _inValue = nf.format(d);
	    	    }catch(NumberFormatException ex){}
	    }
	    else if(formatType != null && formatType.equals("999.999,00")){
	    	nf = NumberFormat.getCurrencyInstance(Locale.GERMAN);

	    	try{
	    	  	  d = Double.parseDouble(_inValue);
	    	  	 _inValue = nf.format(d);
	    	  	 _inValue = _inValue.substring(1);
	    	    }catch(NumberFormatException ex){}
	    }
	    
	    
	    return _inValue;
	  }
	  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String testStr = "1225.0338";
		System.out.println("testing string: "+testStr);
		testStr = formatCurrency(testStr, "ROUND3", "999,999.00");
		System.out.println("after rounding by 2 and formatting by 999,999.00: "+testStr);
	}
}
