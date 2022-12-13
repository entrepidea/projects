"""
to determine if we have three consecutive odds in an array
https://leetcode.com/problems/three-consecutive-odds/

date: 10/15/22

"""
from typing import List
def cons_odds(arr) -> bool:
	for i in range(0, len(arr)-2):
		if arr[i]%2 and arr[i+1]%2 and arr[i+2]%2:
			print(f'{arr[i]},{arr[i+1]} and {arr[i+2]}')
			return True

	return False

#https://leetcode.com/problems/three-consecutive-odds/discuss/892927/Python-beats-99-use-string
def cons_odds_smart_ass(arr: List[int]) -> bool:
	return '111' in ''.join([str(i%2) for i in arr])

if __name__ == '__main__':
	arr = [1,2,34,3,4,5,7,23,12]
	print(cons_odds(arr))
	print(cons_odds_smart_ass(arr))
