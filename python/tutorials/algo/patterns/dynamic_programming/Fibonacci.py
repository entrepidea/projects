output = [None]*1000
"""
it looks like recursion, only the intermediate results are saved.

source: algorithm illustrated in Python (Chinese book)
date: 03/20/21

"""
def fibonacci(n):
    result = output[n]

    if result is None:

        if n == 0:
            result = 1
        elif n == 1:
            result = 1
        else:
            result = fibonacci(n-1)+fibonacci(n-2)
        output[n] = result
    return result

print(fibonacci(10))