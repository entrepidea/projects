
from functools import reduce
from operator import mul

def fac(n):
    return reduce (lambda a,b: a*b, range(1,n))


def fac2(n):
    return reduce(mul, range(1,n))

def main():
    print(fac(10))
    print(fac2(10))

if __name__  == "__main__":
    main()
