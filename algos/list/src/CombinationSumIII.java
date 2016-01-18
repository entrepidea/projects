//Find all possible combinations of k numbers that add up to a number n, given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.
//
//Ensure that numbers within the set are sorted in ascending order.
//
//Example 1:
//
//Input: k = 3, n = 7
//
//Output:
//
//[[1,2,4]]

//Example 2:
//Input: k = 3, n = 9
//Output:
//[[1,2,6], [1,3,5], [2,3,4]]

//Credits:
//Special thanks to @mithmatt for adding this problem and creating all test cases.

//Source: https://leetcode.com/problems/combination-sum-iii/

public class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        return combinationSum3(k, n, 1); //the last parameter is start point
    }
   
    public List<List<Integer>> combinationSum3(int k, int rem, int start) {
        List<Integer> lst = new ArrayList<Integer>();
        List<List<Integer>> lsts = new ArrayList<List<Integer>>();
            //warning: ArrayList<List<Integer>> cannot be replace with
            //List<List<Integer>> or List<ArrayList<Integer>> or
            //ArrayList<ArrayList<Integer>>
        if (k <= 0 || rem < start)
           return lsts; //no solution
          
        if (k == 1) //only one option is left
           if (rem >= start && rem <= 9) {
               lst.add(rem);
               lsts.add(lst);
               return lsts;
           }
           else return lsts;
          
        //k > 1
        List<List<Integer>> lsts2;
        List<Integer> lst2;
        for (int i = start; i <= Math.min(9, rem-i); i++) {
            lsts2 = combinationSum3(k-1, rem-i, i+1);
            for (int j = 0; j < lsts2.size(); j++) {
                lst2 = lsts2.get(j);
                lst2.add(0,i);
                lsts.add(lst2);
                   //warning: lsts.add(lst2) does not work.
                   //Each time when lst2 is added to lsts2,
                   //lsts2.size() is increased by 1,
                   //even though j is increase by 1,
                   //j is forever smaller than lsts2.size(),
                   //hence an endless loop is entered.
            }
        }
       
        return lsts;
    }
}
