"""
You are given a string s consisting only of the characters '0' and '1'. In one operation, you can change any '0' to '1' or vice versa.

The string is called alternating if no two adjacent characters are equal. For example, the string "010" is alternating, while the string "0100" is not.

Return the minimum number of operations needed to make s alternating.


https://leetcode.com/problems/minimum-changes-to-make-alternating-binary-string/?envType=list&envId=x581v2qg

06/17/23

"""

def foo(s:str)->int:
	cnt = 0
	l = list(s)
	for i in range(len(l)-1):
		if l[i+1] == l[i]:
			l[i+1] = str(1-int(l[i]))
			cnt += 1
	return cnt

if __name__ == '__main__':
	s = '0100'
	print(foo(s))
	s = '10'
	print(foo(s))
	s = '1111'
	print(foo(s))

