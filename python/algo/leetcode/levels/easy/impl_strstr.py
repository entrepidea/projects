"""
find sub string in a string and return the index of the first letter.
https://leetcode.com/problems/implement-strstr/
date: 10/08/21

"""
def strstr(haystack, needle):

    if len(needle) == 0:
        return 0

    h = list(haystack)
    n = list(needle)

    for i in range(0,len(h)):
        if h[i] == n[0]:
            pt = i+1
            for j in range(1,len(n)):
                if h[pt] != n[j]:
                    continue
                pt += 1
            return i                
    return -1

if __name__ == '__main__':
    haystack = 'hello'
    needle = 'll'
    print(strstr(haystack,needle))
    needle = 'o'
    print(strstr(haystack,needle))
    needle = 'he'
    print(strstr(haystack,needle))
    needle = 'ell'
    print(strstr(haystack,needle))

    haystack = "aaaaa"
    needle = "bba"
    print(strstr(haystack,needle))
    

    haystack = ""
    needle = ""
    print(strstr(haystack,needle))
