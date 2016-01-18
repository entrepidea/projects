//source: https://leetcode.com/problems/first-bad-version/

//You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of your product fails the quality check. Since each version is developed based on the previous version, all the versions after a bad version are also bad. 

//Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.

//You are given an API bool isBadVersion(version) which will return whether version is bad. Implement a function to find the first bad version. You should minimize the number of calls to the API.

//Credits:
//Special thanks to @jianchao.li.fighter for adding this problem and creating all test cases.

/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        return firstBadVersion(1, n);
    }
    
    public int firstBadVersion(int start, int end) {
        if (start > end)
           return -1; //this return will not happen, since there is a bad version.
        else if (start == end)
                return start;
                
        int mid = //(start + end)/2;  //Warning: (start + end) might be out of boundary
                  //The above line causes stack overflow error
                  //2126753390 versions, 1702766719 is the first bad version.
                  start/2 + end/2;
        if (isBadVersion(mid)) {
           if (mid-1 < start || !isBadVersion(mid-1))
              //mid-1 < start || !isBadVersion(mid-1)
              //means either mid is the first version to check (and mid is bad) or
              //mid-1 version is good but mid is bad,
              //so mid is the first bad version.
              //Note that we want to find out the FIRST bad version, 
              //not just any bad version.
              return mid;
           else //mid-1 >= start && isBadVersion(mid-1),
                //ie, the first bad version happens in [start, mid-1]
                return firstBadVersion(start, mid-1);
        }
        else //mid is good, so [1..mid] are all good
             return firstBadVersion(mid+1, end);
    }
}
