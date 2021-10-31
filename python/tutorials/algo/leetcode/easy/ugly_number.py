"""
to determine if a number is an ugly one (whose prime factors include only 2,3, or 5)
Date: 10/27/21

"""
def ugly(num) -> bool:
    if num/2 == 1 or num/3 == 1 or num/5 ==1:
        return True
    if num%2 == 0:
        return ugly(num/2)
    else:
        if num%3 == 0:
            return ugly(num/3)
        else:
            if num%5 == 0:
                return ugly(num/5)
            else:
                return False

if __name__ == '__main__':
    print(ugly(6))
    print(ugly(8))
    print(ugly(14))
    print(ugly(15))
    print(ugly(99))
    print(ugly(25))

