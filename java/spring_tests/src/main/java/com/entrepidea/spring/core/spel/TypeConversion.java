package com.entrepidea.spring.core.spel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class TypeConversion {
static class Simple{
	public List<Boolean> bList = new ArrayList<>();
	{
		bList.add(true);
		bList.add(false);
	}
}

	public Boolean getBValue(String bVal){
		Simple simple = new Simple();
		StandardEvaluationContext simpleContext = new StandardEvaluationContext(simple);
		ExpressionParser parser = new SpelExpressionParser();
		parser.parseExpression("bList[0]").setValue(simpleContext, bVal);
		Boolean b = simple.bList.get(0);
		return b;
	}
}
