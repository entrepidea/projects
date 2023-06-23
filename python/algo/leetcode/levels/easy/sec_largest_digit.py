"""
Given an alphanumeric string s, return the second largest numerical digit that appears in s, or -1 if it does not exist.

An alphanumeric string is a string consisting of lowercase English letters and digits.

https://leetcode.com/problems/second-largest-digit-in-a-string/

06/17/23

"""

def foo(s:str)->int:
	digits = list(filter(lambda x:x.isdigit(),list(s)))
	print(f'digits={digits}')

	if len(digits)==0 or len(set(digits))==1:
		return -1
	digits = sorted(list(set(digits)),reverse=True)
	print(digits)
	return digits[1]	

if __name__ == '__main__':
	s = 'dfa12321afd'
	print(foo(s))
	s = 'abc1111'
	print(foo(s))
	s = 'abc'
	print(foo(s))
