"""
The k-beauty of an integer num is defined as the number of substrings of num when it is read as a string that meet the following conditions:

It has a length of k.
It is a divisor of num.
Given integers num and k, return the k-beauty of num.

https://leetcode.com/problems/find-the-k-beauty-of-a-number/
11/03/22

"""
from typing import List

def k_b(num : int, digits :List[str], k:int) ->int:

	if len(digits)<k:
		return 0

	rlt = 0
	for i in range(len(digits)-k+1):
		d = int(digits[i])
		for j in range(1,k):
			d = d*10 + int(digits[i+j])
		if d == 0:
			continue
		if num%d == 0:
			print(f'the satisfying divider of number {num} is: {d}')
			rlt += 1

	return rlt		
			

#slide window solution
#https://leetcode.com/problems/find-the-k-beauty-of-a-number/discuss/2038166/Python-straightforward-with-left-and-right-pointers.-Sliding-window
def k_b2(num : int, k : int) -> int:
	l = 0
	r = k
	cnt = 0
	num = str(num)
	while r<= len(num):
		n = int(num[l:r])
		#print(f'num={num}, n={n},len of number is {len(num)}')
		if not n:
			l += 1
			r += 1
			continue

		if int(num)%n == 0:
			#print(f'num={int(num)}, n={n}')
			cnt += 1
			
		l += 1
		r += 1

	return cnt			


if __name__ == '__main__':
	"""
	num = 240
	k = 2
	digits = [x for x in str(num)]
	print(f'There are {k_b(num, digits, k)} K-beauty numbers for number: {num}')
	num = 430043
	k = 2
	digits = [x for x in str(num)]
	print(f'There are {k_b(num, digits, k)} K-beauty numbers for number: {num}')
	num = 120
	k = 2
	digits = [x for x in str(num)]
	print(f'There are {k_b(num, digits, k)} K-beauty numbers for number: {num}')

	"""

	num = 240
	k = 2
	digits = [x for x in str(num)]
	print(f'There are {k_b2(num, k)} K-beauty numbers for number: {num}')
	num = 430043
	k = 2
	digits = [x for x in str(num)]
	print(f'There are {k_b2(num, k)} K-beauty numbers for number: {num}')
	num = 120
	k = 2
	digits = [x for x in str(num)]
	print(f'There are {k_b2(num, k)} K-beauty numbers for number: {num}')
