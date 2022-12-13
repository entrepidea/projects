"""
You are given the array paths, where paths[i] = [cityAi, cityBi] means there exists a direct path going from cityAi to cityBi. Return the destination city, that is, the city without any path outgoing to another city.

It is guaranteed that the graph of paths forms a line without any loop, therefore, there will be exactly one destination city.

https://leetcode.com/problems/destination-city/

12/03/22

"""
def dest(cities : [[str]])->str:
	for i in range(len(cities)):
		is_destination = True
		d = cities[i][1]
		for j in range(len(cities)):
			if i==j:
				continue
			if d == cities[j][0]:
				is_destination = False
				break
		if is_destination == True:
			return d
	return ""		
			

"""
Explanation:
map elements in a list of lists to a set
B is the destination set and A is the start point set
B - A gives you the one that corresponding to a destination and then you pop it out of the set

truly pythonic
https://leetcode.com/problems/destination-city/discuss/609770/Clean-Python-3-Set-in-two-lines

12/04/22

"""
from typing import List
def dest2(paths : List[List[str]])->str:
	A,B = map(set, zip(*paths))
	print(f'A={A},B={B}')
	return (B-A).pop()
			
if __name__ == '__main__':
	#paths = [["London","New York"],["New York","Lima"],["Lima","Sao Paulo"]]
	#print(dest(paths))
	#paths = [["B","C"],["D","B"],["C","A"]]
	#print(dest(paths))
	paths = [["London","New York"],["New York","Lima"],["Lima","Sao Paulo"]]
	print(dest2(paths))
	paths = [["B","C"],["D","B"],["C","A"]]
	print(dest2(paths))
