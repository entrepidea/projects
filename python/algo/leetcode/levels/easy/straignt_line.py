"""
You are given an array coordinates, coordinates[i] = [x, y], where [x, y] represents the coordinate of a point. Check if these points make a straight line in the XY plane.
https://leetcode.com/problems/check-if-it-is-a-straight-line/

10/22/22

"""
import math
from typing import List
def straight(cor : List[List]) -> bool:
	start = cor[0]
	for i in range(1, len(cor)-1):
		ang = abs(cor[i][1] - start[1])/abs(cor[i][0]-start[0])
		ang2 = abs(cor[i+1][1] - start[1])/abs(cor[i+1][0]-start[0])
		if not math.isclose(ang,ang2,rel_tol=1e-5):
			return False
	return True

if __name__ == '__main__':
	cor = [[1,2],[2,3],[3,4],[4,5],[5,6],[6,7]]
	print(straight(cor))
	cor = [[1,1],[2,2],[3,4],[4,5],[5,6],[7,7]]
	print(straight(cor))
