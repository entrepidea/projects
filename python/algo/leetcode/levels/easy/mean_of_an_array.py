"""
find out the mean of an array after removing its 5% smallest and largest elements
https://leetcode.com/problems/mean-of-array-after-removing-some-elements/
09/05/22
"""

def mean(arr) -> int:
	arr.sort()
	print(f'sorted array = {arr}')

	l = len(arr)
	pct5 = (int)(0.05*l)
	print(f'len={l},five_pct={pct5}')

	five_pct_smallest = arr[:pct5]
	print(f'smallest 5% = {five_pct_smallest}')
	five_pct_largest = arr[:l-pct5-1:-1]
	print(f'largest 5% = {five_pct_largest}')
	new_arr = arr[pct5:l-pct5]
	print(f'new array={new_arr}')
	return sum(new_arr)/len(new_arr)

if __name__ == '__main__':
	#arr = [1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3]
	#print(mean(arr))
	arr = [6,0,7,0,7,5,7,8,3,4,0,7,8,1,6,8,1,1,2,4,8,1,9,5,4,3,8,5,10,8,6,6,1,0,6,10,8,2,3,4]
	print(f'mean of the new array: {mean(arr)}')
