"""
You are given an integer array arr. Sort the integers in the array in ascending order by the number of 1's in their binary representation and in case of two or more integers have the same number of 1's you have to sort them in ascending order.

Return the array after sorting it.

https://leetcode.com/problems/sort-integers-by-the-number-of-1-bits/

11/11/22

"""
#one line sulution in in python
#https://leetcode.com/problems/sort-integers-by-the-number-of-1-bits/discuss/2697450/Python-or-1-liner-lambda-key
def s(arr : [int])->[int]:
	return sorted(arr, key=lambda item:(str(bin(item))[2:].count('1'),item))

if __name__ == '__main__':
	arr = [0,1,2,3,4,5,6,7,8]
	print(s(arr))
	arr = [1024,512,256,128,64,32,16,8,4,2,1]
	print(s(arr))
