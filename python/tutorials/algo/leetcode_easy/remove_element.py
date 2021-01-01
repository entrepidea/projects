
def bubble_sort(arr):
    for i in range(1,len(arr),1):
        is_sorted = True
        for j in range(0, len(arr)-1,1):
            if arr[j] > arr[j+1]:
                arr[j],arr[j+1] = arr[j+1],arr[j]
                is_sorted = False

            if is_sorted:
                break



def remove(arr, ele):
    bubble_sort(arr)
    ##TODO

def main():
    arr = [3,2,2,3]
    ele = 3
    #remove(arr,ele)
    bubble_sort(arr)
    

if __name__ == '__main__':
    main()