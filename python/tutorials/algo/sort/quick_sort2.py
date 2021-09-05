'''
quick sort - idea: pick up an arbitary element (first one, for example), any other elements less than it are put on its left, other on its right.
work on the left and right sections with the same idea till the iterations exhaust
from the chinese book, algorithm illustrated. 

date: 09/04/21

Jonathan Yee.

'''
def qs(data,start_idx, end_idx):

	if start_idx >= end_idx:
		return

	i = start_idx+1
	j = end_idx
	key = data[start_idx]
	while i<=j:
		while data[i]<key:
			i += 1
		while data[j]>key:
			j-= 1

		if i<=j:
			data[i],data[j] = data[j],data[i]
		
	data[start_idx],data[j] = data[j], data[start_idx]
	#print('i=%d,j=%d'%(i,j))
	#print(data)

	#print(data[start_idx:j])
	qs(data,start_idx,j-1)
	#print(data[j+1:])
	#print('j=%d,end_idx=%d'%(j,end_idx))
	qs(data,j+1,end_idx)
	

if __name__ == '__main__':
	data = [4,35,10,6,42,3,79,12,62,18,51,23]
	print('original data:')
	print(data)
	qs(data,0,len(data)-1)
	print('sorted data:')
	print(data)

