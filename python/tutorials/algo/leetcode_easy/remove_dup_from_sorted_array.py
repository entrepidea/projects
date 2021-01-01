"""

https://leetcode.com/problems/remove-duplicates-from-sorted-array/

date: 03/21/19

"""



def new_arr(arr):
    idx = 0
    mov = idx+1
    while mov < len(arr):
        data = arr[idx]
        while data == arr[mov]:
            mov +=1
            if mov>=len(arr):
                break

        if mov < len(arr):
            idx += 1
            arr[idx] = arr[mov]
            mov +=1

    return idx+1

def main():
    arr = [1,1,2,2,3]
    l = new_arr(arr)
    print('the length is: %d'%l)
    for i in range(0,l,1):
        print(arr[i])

if __name__ == '__main__':
    main()