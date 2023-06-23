"""
You are given an array of distinct integers arr and an array of integer arrays pieces, where the integers in pieces are distinct. Your goal is to form arr by concatenating the arrays in pieces in any order. However, you are not allowed to reorder the integers in each array pieces[i].

Return true if it is possible to form the array arr from pieces. Otherwise, return false.

https://leetcode.com/problems/check-array-formation-through-concatenation/

04/03/23


"""
#https://www.geeksforgeeks.org/check-whether-an-array-is-subarray-of-another-array/
def isSubArray(A, B, n, m):
     
    # Two pointers to traverse the arrays
    i = 0; j = 0;
 
    # Traverse both arrays simultaneously
    while (i < n and j < m):
 
        # If element matches
        # increment both pointers
        if (A[i] == B[j]):
 
            i += 1;
            j += 1;
 
            # If array B is completely
            # traversed
            if (j == m):
                return True;
         
        # If not,
        # increment i and reset j
        else:
            i = i - j + 1;
            j = 0;
         
    return False;

def foo(arr : [int], pieces:[[int]])->bool:
	for piece in pieces:
		if not isSubArray(arr,piece,len(arr),len(piece)):
			return False
	return True

if __name__ == '__main__':

	arr = [15,88]
	pieces = [[88],[15]]
	print(f'arr={arr}, pieces={pieces}, result={foo(arr,pieces)}')
	
	arr = [49,18,16]
	pieces = [[16,18,49]]
	print(f'arr={arr}, pieces={pieces}, result={foo(arr,pieces)}')

	arr = [91,4,64,78]
	pieces = [[78],[4,64],[91]]
	print(f'arr={arr}, pieces={pieces}, result={foo(arr,pieces)}')

