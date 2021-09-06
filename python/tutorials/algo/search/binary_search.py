"""
binary search, recursive way.

09/06/21, Jonathan Yee

"""

def bin_search(data,start_idx, end_idx, val, count):

	if count >= 10: #the count is added for debugging purpose, to avoid endless loop.
		return None
	count += 1
	
	if start_idx >= end_idx:
		return None

	if val == data[start_idx]:
		return start_idx

	if val == data[end_idx]:
		return end_idx

	mid = start_idx + (end_idx-start_idx)//2
	if val == data[mid]:
		return mid

	if val < data[mid]:
		print('start_idx=%d,end_idx=%d,mid=%d,val=%d,data[mid]=%d'%(start_idx,end_idx,mid, val, data[mid]))
		return bin_search(data,start_idx,mid,val, count)
	else:	
		print('start_idx=%d,end_idx=%d,mid=%d,val=%d,data[mid]=%d'%(start_idx,end_idx,mid, val, data[mid]))
		return bin_search(data,mid, end_idx, val, count)

if __name__ == '__main__':
	data = []
	for i in range(0,10):
		for j in range(0,5):
			data.append(i*8+j)
	
	print(data)

	val = 12

	count = 0
	idx = bin_search(data, 0, len(data)-1, val, count)	
	if idx is None:
		print('value[%d] not found!'%val)
	else:
		print('found %d in index[%d]'%(data[idx],idx))
