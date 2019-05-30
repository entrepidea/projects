"""

https://leetcode.com/problems/longest-common-prefix/
date: 03/09/19

"""

def longest_prex(arr):
    arr = sorted(arr, key=len)
    first_str = arr[0]
    rest_arr = arr[1:]

    l = len(first_str)
    count = 0
    prex = []
    while count<l:
        c = first_str[count]
        is_same = True
        for str in rest_arr:
            if str[count] != c:
                is_same = False
                break

        if is_same:
            prex.append(c)
            count +=1
        else:
            break

    return prex

def main():
    prex = longest_prex(["flower","flow","flight"])
    print(prex)
    prex = longest_prex(["dog","racecar","car"])
    print(prex)

if __name__ == '__main__':
    main()