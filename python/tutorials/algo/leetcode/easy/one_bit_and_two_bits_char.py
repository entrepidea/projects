"""
We have two special characters:

The first character can be represented by one bit 0.
The second character can be represented by two bits (10 or 11).
Given a binary array bits that ends with 0, return true if the last character must be a one-bit character.
https://leetcode.com/problems/1-bit-and-2-bit-characters/

NOTE: the whole idea is that numbers of continuous 1s gonna be even if it's NOT followed by 0.


Date: 12/15/21


"""
def match(arr : [int]) -> bool:
    new_arr = arr[:-1]
    p = 0
    cnt = 0
    while p<len(new_arr):
        while p < len(new_arr) and new_arr[p] == 0:
            cnt = 0
            p += 1
        while p < len(new_arr) and new_arr[p] == 1:
            cnt += 1
            p += 1

    if cnt % 2 != 0:
        return False
    else:
        return True

if __name__ == '__main__':
    """
    bit_arr = [0,0,0]
    print(f'array={bit_arr}, result={match(bit_arr)}')
    bit_arr = [1,0,0]
    print(f'array={bit_arr}, result={match(bit_arr)}')
    """
    bit_arr = [0,0,0]
    print(f'array={bit_arr}, result={match(bit_arr)}')
    bit_arr = [1,0,0]
    print(f'array={bit_arr}, result={match(bit_arr)}')
    bit_arr = [1,1,1,0]
    print(f'array={bit_arr}, result={match(bit_arr)}')
    bit_arr = [1,1,1,1,0]
    print(f'array={bit_arr}, result={match(bit_arr)}')
    bit_arr = [1,0,1,0,0]
    print(f'array={bit_arr}, result={match(bit_arr)}')
    bit_arr = [1,1,1,0,0]
    print(f'array={bit_arr}, result={match(bit_arr)}')
    bit_arr = [0,1,1,0,0]
    print(f'array={bit_arr}, result={match(bit_arr)}')
    bit_arr = [0,0,1,0,0]
    print(f'array={bit_arr}, result={match(bit_arr)}')
    bit_arr = [0,1,0,1,0]
    print(f'array={bit_arr}, result={match(bit_arr)}')
