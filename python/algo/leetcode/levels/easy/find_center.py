"""
There is an undirected star graph consisting of n nodes labeled from 1 to n. A star graph is a graph where there is one center node and exactly n - 1 edges that connect the center node with every other node.

You are given a 2D integer array edges where each edges[i] = [ui, vi] indicates that there is an edge between the nodes ui and vi. Return the center of the given star graph.

https://leetcode.com/problems/find-center-of-star-graph/

06/18/23

"""
#https://leetcode.com/problems/find-center-of-star-graph/solutions/1108319/o-1-1-liner/
def foo(e:[[int]])->int:
	return e[0][0] if e[0][0] == e[1][0] or e[0][0]==e[1][1] else e[1][0]

if __name__ == '__main__':
	edges = [[1,2],[2,3],[4,2]]
	print(foo(edges))
	edges = [[1,2],[5,1],[1,3],[1,4]]
	print(foo(edges))
