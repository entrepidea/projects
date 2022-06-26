import sys

"""
Insertion sorting. Here is a very good explanation for it:
https://www.tutorialspoint.com/data_structures_algorithms/insertion_sort_algorithm.htm

"""
def insert_sorting(data):
    for i in range(1, len(data),1):
        val_to_insert = data[i]
        hold_pos = i
        while hold_pos > 0 and data[hold_pos] < data[hold_pos-1]:
            data[hold_pos] = data[hold_pos-1] #swap
            data[hold_pos-1] = val_to_insert  #swap
            hold_pos = hold_pos-1
    return data

#another approach, 01/30/19
def insert_sorting2(data):
    for i in range(1, len(data),1):
        for j in range(i, 0, -1):
            if data[j] < data[j-1]:
                temp = data[j-1]
                data[j-1] = data[j]
                data[j] = temp
            else:
                break
    return data

#redo - practice. 07/08/21
#NOTE: for clear explaination, read the relevant section in the Chinese python text book: illustrated algo in Python.
def insert_sorting3(arr):
	for i in range(1, len(arr)):
		for j in range(i):
			if arr[j] > arr[i]:
				temp = arr[i]
				for k in range(i-1, j-1, -1):
					arr[k+1] = arr[k]
				arr[j] = temp

		#print(arr)
	
	return arr 			
				
#redo on 09/30/21, refer to the link below:
#https://mp.weixin.qq.com/s?__biz=MzUyNjQxNjYyMg==&mid=2247485556&idx=1&sn=344738dd74b211e091f8f3477bdf91ee&chksm=fa0e67f5cd79eee3139d4667f3b94fa9618067efc45a797b69b41105a7f313654d0e86949607&scene=21#wechat_redirect
def insert4(data):
    if len(data) == 1:
        return
    for i in range(1,len(data)):
        for j in range(0,i):
            if data[i] < data[j]:
                temp = data[i]
                for k in range(i-1,j-1,-1):
                    data[k+1] = data[k]
                data[j] = temp                    
        #print(data)

#redo 10/02/21
def insert5(data):
    if len(data) == 1:
        return
    for i in range(1,len(data)):
        for j in range(0,i):
            if data[i] < data[j]:
                idx = j
                temp = data[i]
                for k in range(i-1,j-1,-1):
                    data[k+1] = data[k]
                data[j] = temp
                break
                    


def main(argv):
    
	data = [55,23,87,62,99,16,79,11,0,-1,108,94]
	print("original array:")
	print(data)

	insert5(data)
	print("sorted array:")
	print(data)

if __name__ == '__main__':
	main(sys.argv[1:])
