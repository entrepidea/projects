package com.entrepidea.ioc.supports.xml;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;


//note: use of SpEl's Expression interface
public class HelloWorldParser {

	//the same example on the spring doc (http://docs.spring.io/spring/docs/4.2.0.BUILD-SNAPSHOT/spring-framework-reference/htmlsingle/#expressions)
	//uses the string like "''Hello world''", which didn't work
	//see the error and solution from here: 
	//http://stackoverflow.com/questions/29534883/spell-parsing-a-valid-expression-there-is-still-more-data-in-expression
	public String parseIteral(){
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("'Hello World'");
		String msg = (String)exp.getValue();
		return msg;
	}
	
	//parse the method invoker
	public String parserMothedInvoker(){
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("'Hello world'.concat('!')");
		return (String)exp.getValue();
	}
	
	
	//calling Java bean's property 
	public byte[] getStringBytes(){
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("'Hello world'.bytes");
		return (byte[])exp.getValue();
	}
	
	
	
	//parse the contructor
	public String parseConstructor(){
		
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("new String('Hello world').toUpperCase()");
		return (String)exp.getValue();
	}
}
