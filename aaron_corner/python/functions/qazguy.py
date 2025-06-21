"""
This file is used to store functions for use in other files.
"""

#Turn number into binary(base 2) form

def binary(x):
    if x == 0:
        return "0"
    result = ""
    index = 1
    while index <= x:
        index *= 2
    index //= 2
    while index > 0:
        if x >= index:
            x -= index
            result += "1"
        else:
            result += "0"
        index //= 2
    result = int(result)
    return result


#Temperature Converter

def temp(a, b, c):
    if a == "c" and b == "f":
        return (c * 1.8) + 32
    elif a == "c" and b == "k":
        return c + 273
    elif a == "f" and b == "c":
        return (c - 32) * 5 / 9
    elif a == "f" and b == "k":
        return ((c - 32) * 5 / 9) + 273
    elif a == "k" and b == "c":
        return c - 273
    elif a == "k" and b == "f":
        return ((c - 273) * 1.8) + 32

#Math

def triangle(n):
    return n * (n + 1) // 2
def pronic(n):
    return n * (n+1)
