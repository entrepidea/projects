'''
A sliding window moves from the left to the right of an array by 1 position at each move, the windows is k (k<array's length).
Pick up the max of the sliding window at each move, and form an array to return

'''

import random

def main():
    src = random.sample(range(1, 100), 10)
    #src =  [1,3,-1,-3,5,3,6,7]
    print(src)
    sliding_win_len = 3
    #how many times of interaction?
    moving_iter = len(src) - sliding_win_len +1
    ret_arr = list()
    for i in range(0, moving_iter, 1):
        arr = src[i:i+sliding_win_len]
        #print(arr)
        ret_arr.append(max(arr))

    print(ret_arr)

if __name__ == "__main__":
    main()
