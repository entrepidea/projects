def climb(n):
    if n<=2:
        return n
    return climb(n-1)+climb(n-2)

def climb2(n):
    pre = 1
    cur = 2
    sum = 0
    if n <=2:
        return n
    while n>2:
        sum = pre + cur
        pre = cur
        cur = sum
        n -= 1
    return sum        

if __name__ == '__main__':
    for i in range(1,10):
        print(climb(i), end=' ')
    print()
    for i in range(1,10):
        print(climb2(i), end=' ')
    print()

