"""
We are given a list nums of integers representing a list compressed with run-length encoding.

Consider each adjacent pair of elements [freq, val] = [nums[2*i], nums[2*i+1]] (with i >= 0).  For each such pair, there are freq elements with value val concatenated in a sublist. Concatenate all the sublists from left to right to generate the decompressed list.

Return the decompressed list.

https://leetcode.com/problems/decompress-run-length-encoded-list/

09/11/22

"""

def decompress(arr:[])->[]:
	d = {}
	freqs = arr[::2]
	vals = arr[1::2]
	print(f'freqs={freqs}, values={vals}')

	new_arr = []
	for i in range(len(freqs)):
		k = 0
		while k < freqs[i]:
			new_arr.append(vals[i])
			k += 1

	return new_arr

if __name__ == '__main__':
	arr = [1,2,3,4]
	print(f'decompressed list:={decompress(arr)}')	
	arr = [1,1,2,3]
	print(f'decompressed list:={decompress(arr)}')	
	
