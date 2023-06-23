"""
You are given a string time in the form of  hh:mm, where some of the digits in the string are hidden (represented by ?).

The valid times are those inclusively between 00:00 and 23:59.

Return the latest valid time you can get from time by replacing the hidden digits.

https://leetcode.com/problems/latest-time-by-replacing-hidden-digits/

06/16/23

"""

def foo(ts:str)->str:
	max_digits = ['2','9','','5','9']
	print(ts)
	i = 0
	ret = []
	for c in ts:
		if c=='?':
			if i == 1 and ret[0] == '2':
				ret.append('3')
			else:
				ret.append(max_digits[i])
		else:
			ret.append(c)

		i += 1

	return ''.join(ret)			

if __name__ == '__main__':
	time = "2?:?0"
	print(foo(time))
	time = "0?:3?"
	print(foo(time))
	time = "1?:22"
	print(foo(time))
