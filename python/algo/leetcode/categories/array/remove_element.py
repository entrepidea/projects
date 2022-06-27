def closed_range(start, stop, step=1):
    dir = 1 if (step > 0) else -1
    return range(start, stop + dir, step)

def test_closed_range(arr):
    for i in closed_range(0,len(arr)-1):
        print(arr[i])
    for i in range(0,len(arr)):
        print(arr[i])

def remove(arr,num) -> []:
    l = len(arr)
    found = 0
    i = 0
    while i < l:
        if arr[i] == num:
            found += 1
            for k in closed_range(i+1, l):
                arr[k-1] = arr[k]
        else:
            i += 1

    new_len = l - found
    return arr[0:new_len]

if __name__ == '__main__':
    arr = [1,2,3,5,5]
    num = 5
    #print(remove(arr,num))
    #test_closed_range(arr)
    arr = [1]
    test_closed_range(arr)


