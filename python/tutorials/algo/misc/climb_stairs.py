'''
classic stair-climbing problem.
https://mp.weixin.qq.com/s?__biz=MzUyNjQxNjYyMg==&mid=2247486904&idx=1&sn=099d5560ab25c0163349dff0c7f51490&chksm=fa0e6239cd79eb2fe6e831d7debba60aa906721d592b8766a944ef88bf91bf82568c20d71891&scene=21#wechat_redirect

09/29/21

'''
#recursive
#problem with this solution is that it's slow (O(n^2)), when n = 50 it also take forever to complete
def climb(n):
    if n == 1 or n ==2:
        return n

    return climb(n-1)+climb(n-2)

#dynamic programming
def climb2(n):
    if n == 1 or n == 2:
        return n
    a = 1
    b = 2
    temp = a+b
    for i in range(3,n+1):
        temp=a+b
        a = b
        b = temp
        #print('a=%d,b=%d,temp=%d'%(a,b,temp))
    return temp

if __name__ == '__main__':
    print(climb(4))
    print(climb(10))
    print(climb(20))
    print(climb2(4))
    print(climb2(10))
    print(climb2(50))
