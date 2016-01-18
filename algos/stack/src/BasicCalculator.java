//source: https://leetcode.com/problems/basic-calculator/
//Implement a basic calculator to evaluate a simple expression string.

//The expression string may contain open ( and closing parentheses ), 
//the plus + or minus sign -, non-negative integers and empty spaces .

//You may assume that the given expression is always valid.

//Some examples:
//"1 + 1" = 2
//" 2-1 + 2 " = 3
//"(1+(4+5+2)-3)+(6+8)" = 23
public class Solution {
    public int calculate(String s) {
        Stack<Integer> operands = new Stack<Integer>();
        Stack<Character> operators = new Stack<Character>();
        
        int i = 0;
        int num, num2;
        char ch, ch2;
        while (i < s.length() ) {
            //throw away spaces
            while (i < s.length() && s.charAt(i) == ' ')
                  i++;
            
            //i < s.length() and s.charAt(i) != ' '
            if (i < s.length() && ((ch = s.charAt(i)) >= '0') && ch <= '9') {
                //if condition is important, or we would entered 0 unnecessarily.
               num = 0;
               while (i < s.length() && ((ch = s.charAt(i)) >= '0') && ch <= '9') {
                     num = num*10 + ch - '0';
                     i++;
               }
               operands.push(num);
            }
            
            //omit possible white spaces  
            while (i < s.length() && s.charAt(i) == ' ')
                  i++;
                  
            if (i == s.length())
               break;
               
            //i < s.length() && s.charAt(i) is not space nor numbers 
            //(no two numbers are adjacent to each other in a valid expression)
            switch (ch = s.charAt(i)) {
                case '(':
                         operators.push(ch);
                         break;
                case ')': //cannot use while-statement to substitute 
                          //for the following if-statement,
                          //we will only match the most recent pair of ().
                          //otherwise, we woule have failed case like
                          //"(3-(5-(8)-(2+(9-(0-(8-(2))))-(4))-(4)))"
                          //The correct answer is 23, but we get 15 instead.
                         if (!operators.empty() && (ch2 = operators.pop()) != '(')
                             operands.push(calculate(operands.pop(), operands.pop(), ch2));
                         break;
                case '+':
                case '-':
                         if (operators.empty() || (ch2 = operators.pop()) == '(')
                            operators.push(ch);
                         else {//ch2 == '+' or '-'
                               //since '+' and '-' are of the same precedence,
                               //while they are both evaluated from left-to-right,
                               //so we need to evaluate the previous + or - 
                               //expression before we process the incoming one.
                              operands.push(calculate(operands.pop(), operands.pop(), ch2));
                              operators.push(ch);
                         }
                         break;
            }
            
            i++;
        }
        
        return calculate(operands, operators);
    }
    
    int calculate(int num, int num2, char ch) {
        if (ch == '+')
           return num + num2;
        else //ch == '-'
            return num2 - num; //note that the stored order of operands
    }
    
    int calculate(Stack<Integer> operands, Stack<Character> operators) {
        while (!operators.empty())
           operands.push(calculate(operands.pop(), operands.pop(), operators.pop()));
           
        return operands.pop();
    }
}
