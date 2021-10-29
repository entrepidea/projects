"""
create an array out of a number,n, in such a way that its elements range from 0 to n. Then present each element in binary format and aggreately sum up the 1s in the binary representation. For example, if the number is 5, the array: [0,1,2,3,4,5]; and one of elements, 3, being 11 in binary, will be represent by 2, because it has two 1s, which make the sum 2.
https://leetcode.com/problems/counting-bits/

Date: 10/29/21

"""

def num_to_bit_arr(num, rlt):
	#print(num)
	if num == 0:
		return
	rlt.insert(0,num%2)
	num //= 2
	num_to_bit_arr(num,rlt)
	
def number_of_ones(num) -> int:
	rlt = []
	num_to_bit_arr(num,rlt)
	return sum(rlt)


if __name__ == '__main__':
	num = 5
	arr = []
	for i in range(num+1):
		arr.append(i)
	rlt = [number_of_ones(i) for i in arr]

	print(rlt)
