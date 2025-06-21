"""
source code is from : https://www.youtube.com/watch?v=g_xesqdQqvA

08/13/23

"""
def bubble(list_a):
	indexing_length = len(list_a) - 1
	sorted = False

	while not sorted:
		sorted = True
		for i in range(0, indexing_length):
			if list_a[i] > list_a[i+1]:
				sorted = False
				list_a[i], list_a[i+1] = list_a[i+1], list_a[i]

	return list_a

print(bubble([3,54,43,53,5345,64,25,5]))
