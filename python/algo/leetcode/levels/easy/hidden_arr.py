"""
There is a hidden integer array arr that consists of n non-negative integers.

It was encoded into another integer array encoded of length n - 1, such that encoded[i] = arr[i] XOR arr[i + 1]. For example, if arr = [1,0,2,1], then encoded = [1,2,3].

You are given the encoded array. You are also given an integer first, that is the first element of arr, i.e. arr[0].

Return the original array arr. It can be proved that the answer exists and is unique.

https://leetcode.com/problems/decode-xored-array/

06/16/23

"""

def foo(encoded:[int], first_one:int)->[int]:
	ret = [0]*(len(encoded)+1)
	ret[0] = first_one
	i=0
	for x in encoded:
		ret[i+1] = (x ^ ret[i])
		i += 1

	return ret

if __name__ == '__main__':
	encoded = [1,2,3]
	first = 1
	print(foo(encoded,first))

	encoded = [6,2,7,3]
	first = 4
	print(foo(encoded,first))
