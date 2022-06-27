

"""

https://leetcode.com/problems/palindrome-number/

date: 03/09/19

"""
def palindrome(num):
    arr = []
    while(num):
        arr.append(num%10)
        num = int(num/10)
    idx = 0;
    last_idx = len(arr)-1

    odd = len(arr)%2

    if odd:
        while arr[idx] == arr[last_idx] and idx!=last_idx:
            idx+=1
            last_idx-=1

        return True if idx==last_idx else False

    else:
        stop = int(len(arr)/2-1)
        while arr[idx] == arr[last_idx] and idx!=stop:
            idx += 1
            last_idx -= 1
        return True if idx == stop else False

def main():
    print(palindrome(12344321))

if __name__ == '__main__':
    main()