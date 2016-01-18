//Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
//
//    Integers in each row are sorted in ascending from left to right.
//    Integers in each column are sorted in ascending from top to bottom.
//
//For example,
//
//Consider the following matrix:
//
//[
//  [1,   4,  7, 11, 15],
//  [2,   5,  8, 12, 19],
//  [3,   6,  9, 16, 22],
//  [10, 13, 14, 17, 24],
//  [18, 21, 23, 26, 30]
//]
//
//Given target = 5, return true.
//
//Given target = 20, return false.

//Source: https://leetcode.com/problems/search-a-2d-matrix-ii/

//Key ideas:
//1. Use binary search and its variant.
//2. Find the row whose first element equals target or
//   the largest row, call it endRow, whose first element < target.
//3. Find the row whose last element equals target or
//   the smallest row, call it startRow, whose largest element > target.
//Note:
//Steps 2 and 3 need to use binary search variant.
//For example, in Step 2, if matrix[midRow][0] < target,
//but midRow+1 <= endRow (means midRow+1 is still in valid range) and
//matrix[midRow+1][0] > target, then we return midRow,
//which is the maximum row whose first element is < target. 
//4. Apply traditonal binary search the rows 
//whose indices fall into [startRow, endRow]. 

public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        //Such a matrix looks like intersected intervals.
        int row = matrix.length;
        if (row == 0 || matrix[0].length == 0)
           return false;
        
        int col = matrix[0].length;   
        if (target < matrix[0][0] || target > matrix[row-1][col-1])
           return false;
           
        //Step 1: binary search the largest row whose first element is <= target
        //why largest row? The target might reside in the first row
        //all the way to that largest row.
        //We do not need to check the rows whose first element is > target.
        int endRow = binSearchCol(matrix, 0, row-1, target);
        
        if (endRow == -1)
           return false;
        else if (matrix[endRow][0] == target)
                return true;
                
        //endRow != -1 but matrix[endRow][0] < target
        //Step 2: binary search the smallest row whose last element is >= target
        //Why smallest row? from those rows on, we need to search target.
        //Then we only need to concentrate on the rows in [startRow, endRow]
        //to do a binary search for the target.
        int startRow = binSearchCol2(matrix, 0, endRow, target);
        if (startRow == -1)
           return false;
        else if (matrix[startRow][col-1] == target)
                return true;
        
        for (int i = startRow; i <= endRow; i++)
            if (binSearch(matrix, i, 0, col-1, target) == true)
               return true;
        
        return false;
    }
    
    //This is a traditional binary search.
    //Search whether the target is in a sorted array (each row is a sorted array).
    boolean binSearch(int[][] matrix, int row, int startCol, int endCol, int target) {
        if (startCol > endCol)
           return false;
           
        int midCol = (startCol + endCol)/2;
        if (matrix[row][midCol] == target)
           return true;
        else if (matrix[row][midCol] < target)
                return binSearch(matrix, row, midCol+1, endCol, target);
             else return binSearch(matrix, row, startCol, midCol-1, target);
    }
    
    //Return rows where target possibly exists (checking from start element in row).
    //That is, either matrix[row][0] == target (row might not be largest)
    //or the maximum row such that matrix[row][0] < target
    int binSearchCol(int[][] matrix, int startRow, int endRow, int target) {
        //All rows between startRow and endRow have been checked.
        if (startRow > endRow)
           return -1;
           
        if (matrix[startRow][0] > target) //no need to check the submatrix starting from startRow
           return -1;
        else if (matrix[startRow][0] == target) //lucky me! Got the target now.
                return startRow;
           
        if (matrix[endRow][0] <= target) //the first element of the last row <= target, stop.
           return endRow;
         
        //startRow < endRow
        //matrix[startRow][0] < target 
        //matrix[endRow][0] > target
        int midRow = (startRow + endRow)/2;
        if (matrix[midRow][0] == target) //lucky! Find one instance of target
           return midRow;
        else if (matrix[midRow][0] > target) {
                if (midRow-1 >= startRow && matrix[midRow-1][0] < target)
                   return midRow-1;
                //midRow-1 < startRow or matrix[midRow-1][0] > target,
                //we can still squeeze from midRow-1.
                else return binSearchCol(matrix, startRow, midRow-1, target);
        }
             else {//matrix[midRow][0] < target
                   if (midRow+1 <= endRow && matrix[midRow+1][0] > target)
                      return midRow;
                   else return binSearchCol(matrix, midRow+1, endRow, target);
             }
    }
    
    //Return rows where target possibly exists (checking from end element in row).
    //That is, either matrix[row][col-1] == target (row might not be the minimum)
    //or the minimum row such that matrix[row][col-1] > target
    int binSearchCol2(int[][] matrix, int startRow, int endRow, int target) {
        //All rows between startRow and endRow have been checked.
        if (startRow > endRow)
           return -1;
           
        int col = matrix[0].length-1;
           
        if (matrix[startRow][col] >= target) //the last element of startRow > target already
           return startRow;
           
        if (matrix[endRow][col] < target) //the last element of endRow < target, no need to try
           return -1;
        else if (matrix[endRow][col] == target) //lucky! Find one instance.
           return endRow;
         
        //startRow < endRow
        //matrix[startRow][col] < target 
        //matrix[endRow][col] > target
        //Find the row whose last element equals target (one instance is enough) or
        //the minimum row whose last element > target
        int midRow = (startRow + endRow)/2;
        if (matrix[midRow][col] == target)
           return midRow;
        else if (matrix[midRow][col] > target) {
                if (midRow-1 >= startRow && matrix[midRow-1][col] <= target)
                   return midRow;
                else return binSearchCol2(matrix, startRow, midRow-1, target);
             }
             else {//matrix[midRow][col] < target
                   if (midRow+1 <= endRow && matrix[midRow+1][col] >= target)
                      return midRow+1;
                   //midRow+1 > endRow || matrix[midRow+1][col] < target
                   else return binSearchCol2(matrix, midRow+1, endRow, target);
             }
    }
}
