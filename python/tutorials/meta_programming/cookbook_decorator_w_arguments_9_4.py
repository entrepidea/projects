# TODO
import logging
from functools import wraps
"""
Show how to pass additional arguments when applying decorator pattern on a function. 

The idea is that the outermost function accepts the desired arguments and makes them available 
to the inner function decorate. The function decorate accepts a function and put a wrapper around it.

The key part is that the wrapped function is allowed to use the argument passed through log.
"""

def logged(level, name=None, message=None):
    """
    Add logging to a function
    """
    def decorate(func):
        log_name = name if name else func.__module__
        log = logging.getLogger(log_name)
        log_msg = message if message else func.__name__

        @wraps(func)
        def wrapper(*arg, **kwarg):
            log.log(level, log_msg)
            return func(*arg, **kwarg)

        return wrapper

    return decorate


@logged(logging.CRITICAL, "add_func", "add two numbers")
def add(x, y):
    return x + y


@logged(logging.CRITICAL)
def spam():
    print("Spam!")


if __name__ == "__main__":
    print(add(3, 4))
    spam()
