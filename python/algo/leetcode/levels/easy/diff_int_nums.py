"""
You are given a string word that consists of digits and lowercase English letters.

You will replace every non-digit character with a space. For example, "a123bc34d8ef34" will become " 123  34 8  34". Notice that you are left with some integers that are separated by at least one space: "123", "34", "8", and "34".

Return the number of different integers after performing the replacement operations on word.

Two integers are considered different if their decimal representations without any leading zeros are different.

https://leetcode.com/problems/number-of-different-integers-in-a-string/

06/19/23

"""

def foo(s:str)->int:
	tmp = []
	l = []
	for c in s:
		if c.isdigit():
			if c != '0':
				tmp.append(c)
		else:
			if len(tmp)!=0:
				l.append(''.join(tmp))
				tmp = []

	if len(tmp)>0:
		l.append(''.join(tmp))

	print(l)
	return len(set(l))

if __name__ == '__main__':
	s = 'a123bc34d8ef34'
	print(foo(s))
	s = 'leet1234code234'
	print(foo(s))
	s = 'a1b01c001'
	print(foo(s))
