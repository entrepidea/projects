"""
There is a biker going on a road trip. The road trip consists of n + 1 points at different altitudes. The biker starts his trip on point 0 with altitude equal 0.

You are given an integer array gain of length n where gain[i] is the net gain in altitude between points i​​​​​​ and i + 1 for all (0 <= i < n). Return the highest altitude of a point.

https://leetcode.com/problems/find-the-highest-altitude/

06/16/23

"""
def foo(g:[int])->int:
	ret = [0]*(len(g)+1)
	ret[0] = 0
	for i in range(0,len(g)):
		ret[i+1] = ret[i]+g[i]		
	print(ret)
	return max(ret)

if __name__ == '__main__':
	gain = [-5,1,5,0,-7]
	print(foo(gain))
	gain = [-4,-3,-2,-1,4,3,2]
	print(foo(gain))
