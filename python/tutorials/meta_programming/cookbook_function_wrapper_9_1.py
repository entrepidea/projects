from functools import wraps
import time

def timethis(func):
    """
    decorator is a function that accepts a function as input and ouput a new function.
    below is a decorator that report the execution time
    """
    @wraps(func)
    def wrapper(*args, **kwargs):
        start = time.time()
        result = func(*args, **kwargs)
        end = time.time()
        print(func.__name__, end - start)
        return result
    return wrapper

@timethis
def countdown(n):
    while n > 0:
        n -= 1

if __name__ == '__main__':
    countdown(1000000)
    countdown(100000000)
    # attribute __wrapped__ can be used to retrieve the original function
    countdown.__wrapped__(1000000)
