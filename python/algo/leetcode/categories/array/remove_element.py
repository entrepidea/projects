"""

https://www.cspiration.com/#/leetcodeClassification/codeDetail?id=27&language=en

remove specified number from an array, return the shrunk array.

date: 06/26/22

"""

def remove(arr,num) -> []:
    l = len(arr)
    found = 0
    i = 0
    new_len = l
    while i < l:
        if arr[i] == num:
            k = i+1
            i = 0
            while k < l:
                arr[k-1] = arr[k]
                k += 1
            l -= 1
            #print(f'arr={arr[:l]},length={l},i={i}')
        else:    
            i+=1
    return arr[:l]

if __name__ == '__main__':
    
    arr = [1,2,3,5,5]
    num = 5
    print(f'origin arr={arr}, remove:{num}, final arr={remove(arr,num)}')
    arr = [1,2,3,5,5]
    num = 2
    print(f'origin arr={arr}, remove:{num}, final arr={remove(arr,num)}')
    arr = [1,2,3,5,5]
    num = 1
    print(f'origin arr={arr}, remove:{num}, final arr={remove(arr,num)}')
    arr = [1,2,3,1,5]
    num = 1
    print(f'origin arr={arr}, remove:{num}, final arr={remove(arr,num)}')
    
    arr = [1,2,3,1,1]
    num = 1
    print(f'origin arr={arr}, remove:{num}, final arr={remove(arr,num)}')
