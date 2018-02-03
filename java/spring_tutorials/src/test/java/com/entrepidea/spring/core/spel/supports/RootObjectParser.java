package com.entrepidea.spring.core.spel.supports;

import java.util.GregorianCalendar;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Evaluate expression using Spring's Expression interface
 * 
 * The more common usage of SpEL is to provide an expression string that is evaluated against a specific object instance (called the root object). 
 * 
 * http://docs.spring.io/spring/docs/4.2.0.BUILD-SNAPSHOT/spring-framework-reference/htmlsingle/#expressions-evaluation
 * 
 * */
public class RootObjectParser {
	public static String parseInventorClass(){
		// Create and set a calendar
		GregorianCalendar c = new GregorianCalendar();
		c.set(1856, 7, 9);

		// The constructor arguments are name, birthday, and nationality.
		Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");

		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = (Expression) parser.parseExpression("name");

		EvaluationContext context = new StandardEvaluationContext(tesla);
		String name =  (String)exp.getValue(context);
		return name;
	}
}
