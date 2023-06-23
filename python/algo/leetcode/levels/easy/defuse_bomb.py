"""
You have a bomb to defuse, and your time is running out! Your informer will provide you with a circular array code of length of n and a key k.

To decrypt the code, you must replace every number. All the numbers are replaced simultaneously.

If k > 0, replace the ith number with the sum of the next k numbers.
If k < 0, replace the ith number with the sum of the previous k numbers.
If k == 0, replace the ith number with 0.
As code is circular, the next element of code[n-1] is code[0], and the previous element of code[0] is code[n-1].

Given the circular array code and an integer key k, return the decrypted code to defuse the bomb!

https://leetcode.com/problems/defuse-the-bomb/

03/10/23

"""
def foo(code : [], k : int)->[]:
	ret = [0]*len(code)
	for i in range(len(code)):
		if k > 0:
			j = i+1
			total = 0
			for n in range(k):
				if j==len(code):
					j = 0 #reset
				total += code[j]
				j += 1
		elif k==0:
			return [0]*len(code)
		else: #k<0
			j=i-1
			total = 0
			for n in range(-1*k):
				if j<0:
					j=len(code)-1
				total += code[j]
				j -= 1

		ret[i] = total
	
	return ret

#sliding window
#https://leetcode.com/problems/defuse-the-bomb/solutions/3152661/python-3-o-n-sliding-window-with-array-rotation/
def slide(code:[],k:int)->[]:
	ret = []
	if k==0:
		return [0]*len(code)
	absK = abs(k)
	s = sum(code[:absK])
	for i in range(len(code)):
		s = s - code[i]+code[(i+absK)%len(code)]
		ret.append(s)
	if k<0:
		ret = ret[k-1:]+ret[:k-1]
	
	return ret

			

if __name__ == '__main__':
	code = [5,7,1,4]
	k = 3
	print(foo(code,k))
	code = [1,2,3,4]
	k = 0
	print(foo(code,k))
	code = [2,4,9,3]
	k = -2
	print(foo(code,k))

	code = [5,7,1,4]
	k = 3
	print(slide(code,k))
	code = [1,2,3,4]
	k = 0
	print(slide(code,k))
	code = [2,4,9,3]
	k = -2
	print(slide(code,k))
