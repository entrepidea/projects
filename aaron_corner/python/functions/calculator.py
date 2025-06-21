"""
2/7/2025
Welcome to the JavaPython Calculator!
Follow the instructions to get the answer.
"""

#True and False must be uppercase when you are typing them.

import math

first_number = 1  #Replace the number after the equal sign with the first number that you are using.

second_number = 2  #Replace the number after the equal sign with the second number that you are using.

pi = 0  #Make the number after the equal sign to be 0 if your you don't want any of your numbers to change to pi,
#make it 1 if you are making your first number pi, 2 for your second number, and 3 for both

if pi != 0:
    if pi == 1:
        first_number = math.pi
    elif pi == 2:
        second_number = math.pi
    elif pi == 3:
        first_number = math.pi
        second_number = math.pi

""" Now that you have chosen 2 numbers, then you can use an operator for them. """

#Simple arithmetic

add = False  #If you are adding the numbers, then set to True, else set to False

sub = False  #If you are subtracting the numbers, then set to True, else set to False

mul = False  #If you are multiplying the numbers, then set to True, else set to False

div = False  #If you are dividing the numbers, then set to True, else set to False

#Exponential operators

exp = False  #If you are getting your first number to the power of your second number, then set to True, else set to False

xrt = False  #If you are getting the x root of your number, then set to True, else set to False

""" Note that the result of 2 numbers is in the format x(operator)y e.g. x/y or y√x. """

operator = False  #If you are using 2 numbers, then set to True, else set to False.

if operator:
    if add:
        print(first_number + second_number)
    elif sub:
        print(first_number - second_number)
    elif mul:
        print(first_number * second_number)
    elif div:
        print(first_number / second_number)
    elif exp:
        print(first_number ** second_number)
    elif xrt:
        print(first_number ** (1 / second_number))

"""Here are single number functions."""

#These numbers use your first number from before.

function = False  #If you are using only 1 number, then set to True, else set to False.

#Exponential functions

squ = False  #If you are squaring your number, then set to True, else set to False

cub = False  #If you are cubing your number, then set to True, else set to False

sqr = False  #If you are getting the square root of your number, then set to True, else set to False

cur = False  #If you are getting the cube root of your number, then set to True, else set to False

#Trigonometric functions

sin=False

cos=False

tan=False

#Other

fac = True  #If you are getting the factorial of your number, then set to True, else set to False

if function:
    if squ:
        print(first_number ** 2)
    elif cub:
        print(first_number ** 3)
    elif sqr:
        print(math.sqrt(first_number))
    elif cur:
        print(math.cbrt(first_number))
    elif sin:
        print(math.sin(first_number))
    elif cos:
        print(math.cos(first_number))
    elif tan:
        print(math.tan(first_number))
    elif fac:
        print(math.factorial(first_number))