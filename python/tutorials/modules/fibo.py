#testing from interactive console:
#import fibo
#fibo.fib(1000)

def fib(n):   # return Fibonacci series up to n
    result = []
    a, b = 0, 1
    while b < n:
        result.append(b)
        a, b = b, a+b
    return result