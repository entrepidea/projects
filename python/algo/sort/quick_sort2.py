"""
quick sort - idea: pick up an arbitrary element (first one, for example), any other elements less than it are put on its left, other on its right.
work on the left and right sections with the same idea till the iterations exhaust
from the chinese book, algorithm illustrated. 

date: 09/04/21

Jonathan Yee.

"""


def qs(message, start_idx, end_idx):
    if start_idx >= end_idx:
        return

    i = start_idx + 1
    j = end_idx
    key = message[start_idx]
    while i <= j:
        while message[i] < key:
            i += 1
        while message[j] > key:
            j -= 1

        if i <= j:
            message[i], message[j] = message[j], message[i]

    message[start_idx], message[j] = message[j], message[start_idx]
    #print('i=%d,j=%d'%(i,j))
    #print(data)

    #print(data[start_idx:j])
    qs(message, start_idx, j - 1)
    #print(data[j+1:])
    #print('j=%d,end_idx=%d'%(j,end_idx))
    qs(message, j + 1, end_idx)


def sort(arr, start_idx, end_idx):
    if end_idx <= start_idx:
        return
    mark_idx = partition(arr, start_idx, end_idx)
    sort(arr, start_idx, mark_idx - 1)
    sort(arr, mark_idx + 1, end_idx)


#TODO
def partition(arr, start_idx, end_idx):
    mark = start_idx
    pivot = arr[mark]
    for i in range(start_idx + 1, end_idx):
        if arr[i] < pivot:
#            temp = arr[i]
            arr[i] = 0


def qs2(arr):
    return sort(arr, 0, len(arr) - 1)


if __name__ == '__main__':
    data = [4, 35, 10, 6, 42, 3, 79, 12, 62, 18, 51, 23]
    print('original data:')
    print(data)
    qs(data, 0, len(data) - 1)
    print('sorted data:')
    print(data)
