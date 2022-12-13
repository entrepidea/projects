"""
A bus has n stops numbered from 0 to n - 1 that form a circle. We know the distance between all pairs of neighboring stops where distance[i] is the distance between the stops number i and (i + 1) % n.

The bus goes along both directions i.e. clockwise and counterclockwise.

Return the shortest distance between the given start and destination stops.

https://leetcode.com/problems/distance-between-bus-stops/

09/18/22, 10/03/22

NOTE: this provide a simple solution:
https://leetcode.com/problems/distance-between-bus-stops/discuss/2459698/Python-or-94.69-Fast-Python-Solution

"""
from typing import List

def dis(distance: List[int], start:int,end:int) -> int:
	if start > end:
		start, end = end, start
	
	overall_sum = sum(distance)
	route_sum = sum(distance[start:end])
	return min(overall_sum - route_sum, route_sum)
	
if __name__ == '__main__':
	start = 0
	end = 2
	dist = [1,2,3,4]
	print(dis(dist,start,end))
	end = 3
	dist = [1,2,3,4]
	print(dis(dist,start,end))
