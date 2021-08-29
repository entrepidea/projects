#write on 08/28/21, shell algorithm
#idea: 
#1. pick up elements of an array at a specific interval, and sort them using insertion sorting.
#2. reduce the interval and repeat the above, until interval is less than 1
def shell(data):
	interval = len(data)//2
	while interval > 0 : 
		
		print('interval=%d\n'%interval)
		i = 0
		while i<interval:
			j = i
			while j < len(data):
				#compare with previous data and do inserting sort.
				temp = data[j]
				for k in range(i,j,interval):
					if data[k]>data[j]:
						for n in range(j,k,-interval):
							data[n] = data[n-interval]
						data[k] = temp	

				j+=interval

			i += 1 

		interval //=2
	
def main():
	data = [25,1,100,105,50,33,42,10,14,6,88,88,19,27,44,98,7]
	print('---original data array---')
	print(data)
	print('---sorted data array---')
	shell(data)
	print(data)

if __name__ == '__main__':
	main()
