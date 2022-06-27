"""
https://leetcode.com/problems/reverse-integer/
reverse the digits of an integer.

"""



def reverse(target):
    ret = []
    while(target):
        ret.append(target%10)
        target = int(target/10)

    return ret

def main():
    ret = reverse(123)
    [print(i) for i in ret]

if __name__ == '__main__':
    main()