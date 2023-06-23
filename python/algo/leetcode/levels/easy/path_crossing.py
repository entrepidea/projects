"""
Given a string path, where path[i] = 'N', 'S', 'E' or 'W', each representing moving one unit north, south, east, or west, respectively. You start at the origin (0, 0) on a 2D plane and walk on the path specified by path.

Return true if the path crosses itself at any point, that is, if at any time you are on a location you have previously visited. Return false otherwise.

https://leetcode.com/problems/path-crossing/

12/24/22

"""
def crossing(path : str)->bool:
	x = 0
	y = 0
	s =set()
	s.add((x,y))
	for d in path:
		if d == 'N':
			y += 1
		elif d == 'S':
			y -= 1
		elif d == 'W':
			x += 1
		elif d == 'E':
			x -= 1
		if (x,y) in s:
			return True
		else:
			s.add((x,y))
	return False

if __name__ == '__main__':
	path = 'NES'
	print(crossing(path))
	path = 'NESWW'
	print(crossing(path))
	path = 'NESSWW'
	print(crossing(path))
	path = 'NESN'
	print(crossing(path))
