"""
Given an integer n (in base 10) and a base k, return the sum of the digits of n after converting n from base 10 to base k.

After converting, each digit should be interpreted as a base 10 number, and the sum should be returned in base 10.

https://leetcode.com/problems/sum-of-digits-in-base-k/

06/24/23

"""


def foo(n:int,k:int)->int:
	ret = []
	while n!=0:
		ret.append(n%k)
		n //=k

	print(ret)

	return sum(ret)

if __name__ == '__main__':
	n = 34
	k = 6
	print(foo(n,k))
	n = 34
	k = 3
	print(foo(n,k))
	n = 34
	k = 2
	print(foo(n,k))
	n = 34
	k = 8
	print(foo(n,k))


