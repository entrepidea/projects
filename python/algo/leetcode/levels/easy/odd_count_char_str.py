"""
Given an integer n, return a string with n characters such that each character in such string occurs an odd number of times.

The returned string must contain only lowercase English letters. If there are multiples valid strings, return any of them.  

https://leetcode.com/problems/generate-a-string-with-characters-that-have-odd-counts/

11/18/22

"""

#https://leetcode.com/problems/generate-a-string-with-characters-that-have-odd-counts/discuss/532520/JavaC%2B%2BPython-One-Lines
#n&1 is an interesting way to determin if it's an even or odd.
def odd_chars(n : int) -> str:
	return 'b' + 'ab'[n&1]*(n-1)

if __name__ == '__main__':
	n = 4
	print(odd_chars(n))
	n = 9
	print(odd_chars(n))
