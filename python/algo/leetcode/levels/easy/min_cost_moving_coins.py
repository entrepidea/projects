"""
We have n chips, where the position of the ith chip is position[i].
We need to move all the chips to the same position.
https://leetcode.com/problems/minimum-cost-to-move-chips-to-the-same-position/

10/21/22

"""
from typing import List
def mov_coin(pos : List[int]) ->int:
	ret = []
	for i in range(len(pos)):
		s = 0
		#all left to i
		for j in range(i):
			l = abs(pos[j]-pos[i])
			if l%2 == 1:
				s += 1
		#all right to i
		for j in range(i+1,len(pos)):
			l = abs(pos[j]-pos[i])
			if l%2 == 1:
				s += 1
		ret.append(s)

	return min(ret)
						
#consolidate all odd and all even at no cost, resulting in two piles and we pick up the smaller one.
#https://leetcode.com/problems/minimum-cost-to-move-chips-to-the-same-position/discuss/398239/C%2B%2B-3-lines
def mov_coin2(pos : List[int]) ->int:
	odd_even = [0]*2
	for p in pos:
		odd_even[p%2] +=1 
	return min(odd_even[0],odd_even[1])	
		
if __name__ == '__main__':
	pos = [1,2,3]
	print(mov_coin2(pos))
	pos  =[2,2,2,3,3]
	print(mov_coin2(pos))
	pos = [1,1000000000]
	print(mov_coin2(pos))

	
