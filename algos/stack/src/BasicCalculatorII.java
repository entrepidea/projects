//source: https://leetcode.com/problems/basic-calculator-ii/

/*
Implement a basic calculator to evaluate a simple expression string.

The expression string contains only non-negative integers, +, -, *, / operators and empty spaces . The integer division should truncate toward zero.

You may assume that the given expression is always valid.

Some examples:
"3+2*2" = 7
" 3/2 " = 1
" 3+5 / 2 " = 5
Note: Do not use the eval built-in library function.

Credits:
Special thanks to @ts for adding this problem and creating all test cases.
*/

public class Solution {
    public int calculate(String s) {
        Stack<Integer> operands = new Stack<Integer>();
        Stack<Character> operators = new Stack<Character>();
        
        char ch, ch2; //hold characters read from string s
        int num; //hold the extracted integer from string s
        int i = 0;
        int len = s.length();
        while (i < len) {
            //skip white spaces, if any
            while (i < len && (ch = s.charAt(i)) == ' ')
                  i++;
                  
            //extract an integer
            if ((ch = s.charAt(i)) >= '0' && ch <= '9') {
                num = 0;
                while (i < len && (ch = s.charAt(i)) >= '0' && ch <= '9') {
                    num = num*10 + ch - '0';
                    i++;
                }
                operands.push(num);
            }
            
            //skip white spaces, if any
            while (i < len && (ch = s.charAt(i)) == ' ')
                  i++;
                 
            if (i >= len) //needed, or result in string index out of boundary exception
               break; 
               
            //assume the expression is valid, we must encounter operators +, -, *, / now.
            switch (ch = s.charAt(i)) {
                case '+':
                case '-':
                         while (!operators.empty()) //warning: cannot write while as if,
                            //no operator has lower preference than incoming '+' or '-',
                            //ALL previous operators, which may include '+', '-' (b/c of associativity)
                            //or '*' and '/' (b/c of precedence), must be processed,
                            //until the new comer can be pushed into operators stack.
                            //for example, operands<2, 3, 4> operators<-,/> and 
                            //the incoming operator is +.
                            operands.push(calculate(operands.pop(), operands.pop(), operators.pop()));
                         operators.push(ch);
                         break;
                case '*':
                case '/':
                         //The following if can be updated to while without affecting the correctness.
                         //reason: the incoming * or / sees 
                         //at most one layer of * or / on top of operators.
                         //That is because left associativity of * and /,
                         //once we see a * or /, we need to take care of it before pushing
                         //another * or / in operators. So there is never two or more * or /
                         //in operators.
                         if (!operators.empty() && ((ch2 = operators.peek()) == '*' || ch2 == '/'))
                            operands.push(calculate(operands.pop(), operands.pop(), operators.pop()));
                         //if operators is empty or the top element of operators is + or -, push * or / in
                         operators.push(ch);
            }
            
            i++; //move to the next character
        }
        
        return calculate(operators, operands); //operators can be <+ *> and operands can be <2 3 6>
    }
    
    int calculate(int num, int num2, char op) {
        switch (op) {
            case '+': return num + num2;
            case '-': return num2 - num;
            case '*': return num * num2;
            case '/': return num2 / num;
            default: return 0; //needed for compilation purpose
        }
    }
    
    int calculate(Stack<Character> operators, Stack<Integer> operands) {
        while (!operators.empty())
              operands.push(calculate(operands.pop(), operands.pop(), operators.pop()));
              
        return operands.pop();
    }
}
