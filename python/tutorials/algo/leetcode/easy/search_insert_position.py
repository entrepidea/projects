"""
TODO: not finished yet
https://leetcode.com/problems/search-insert-position/

"""
def insert_pos(arr, target):
    first_part = arr[:len(arr)//2]
    second_part = arr[len(arr)//2:]
    print(first_part)
    print(second_part)

if __name__ == '__main__':
    num = [1,3,4,5,6]
    target = 5
    insert_pos(num,target)

