'''
the following code is from the book algo illustrated (Chinese)

TODO error occurred in running - check later

08/01/21

'''
SIZE=8

def showData(data):
	for i in range(SIZE):
		print('%3d'%data[i], end=' ')
	print()

def shell(data, size):
	k=1
	jmp = SIZE//2
	while jmp!=0:
		for i in range(jmp, size):
			tmp = data[i]
			j = i - tmp
			while tmp<data[i] and j>=0: #insertion sorting
				data[j+jmp] = data[j]
				j = j - tmp
			data[jmp+j] = tmp

		print('#%d:' %k, end = ' ')
		k += 1
		showData(data)
		print('----------------------------------------------')
		jmp = jmp//2
		
def main():
	data = [16,25,39,27,12,8,45,63]
	print('original data set:')
	showData(data)
	print('-----------------------------------------------')
	shell(data,SIZE)

	
if __name__ == '__main__':
	main()
