//Given a string containing just the characters '(', ')', '{', '}', '[' and ']',
//determine if the input string is valid.

//The brackets must close in the correct order, "()" and "()[]{}" are all valid
//but "(]" and "([)]" are not.

public class Solution {
    public boolean isValid(String s) {
        int i = 0;
        int len = s.length();
        Stack<Character> pStack = new Stack<Character>();
           //Cannot write as Stack<Character> pStrack = new LinkedList<Character>();
       
        char ch, ch2;
        while (i < len) {
            ch = s.charAt(i);
           
            if (ch == '[' || ch == '{' || ch == '(')
               pStack.push(ch);
            else if (ch == ']' || ch == '}' || ch == ')') {
                    if (pStack.isEmpty())
                       return false;
                   
                    ch2 = pStack.pop();
                    if ( !isMatch(ch2, ch) )
                       return false;
            }
           
            i++;
        }
       
        if (pStack.isEmpty())
           return true;
        return false;
    }
   
    boolean isMatch(char ch, char ch2) {
        if (ch == '(' && ch2 == ')' ||
            ch == '[' && ch2 == ']' ||
            ch == '{' && ch2 == '}')
            return true;
       
        return false;
    }
}
