"""
Find 3 three-digit numbers that use digits 1 to 9 once e.g. not 163, 234, and 489 because the 3 appears twice.
Also, the numbers have to be in the ratio 1:2:3

created on: 7/8/2025 by Aaron
updated on: 07/12/2025 by Jon.
"""
#find an array of 3-digit numbers that are divisible by 3.
def numbers_divisible_by_3():
    numbers = []
    for i in range(1, 10):
        numbers.append(i)
    index = 987
    candidates = []
    while index >= 369:
        candidates.append(index)
        index -= 3
    return candidates

#break a 3-digit numbers into hundreds, tens and ones, which one to return is up to the 2nd parameter in the funtion.
def digit(x, y):
    if x in range(100, 1000):
        hundreds = x // 100
        tens = (x // 10) - (10 * hundreds)
        ones = x - ((x // 10) * 10)
        if y == 1:
            return ones
        elif y == 2:
            return tens
        elif y == 3:
            return hundreds
    else:
        raise SyntaxError("Out of range, only 3 digit numbers")

#upgraded way to implement the "digit" function, which returns the hundreds, tens and ones of a 3-digit number
def digit2(x):
    a = x//100
    b = x%100//10
    c=x%100%10
    return a,b,c

#create an array of 10 elements and set each of them to be 0
def reset_checker():
    checker = []
    for i in range(0, 10):
        checker.append(0)
    return checker

#check if the digit of hundreds, tens and ones has been used.
def found(checker, ones, tens, hundreds):
    if ones==0 or tens==0 or hundreds==0:# 0 as part of the numbers is not allowed.
        return True
    if checker[ones] == 0:#if not use, set the element in the array whose index coincides with the to-be-checked digit to be 1, indicating that this slot is "taken".
        checker[ones] = 1
    else:
        return True
    if checker[tens] == 0:
        checker[tens] = 1
    else:
        return True
    if checker[hundreds] == 0:
        checker[hundreds] = 1
    else:
        return True

    return False

def tester1():
    candidates = numbers_divisible_by_3()
    for i in candidates:
        checker = reset_checker()
        ones, tens, hundreds = digit(i, 1), digit(i, 2), digit(i, 3)
        if not found(checker, ones, tens, hundreds):
            j = i // 3
            ones, tens, hundreds = digit(j, 1), digit(j, 2), digit(j, 3)
            if not found(checker, ones, tens, hundreds):
                k = 2 * j
                ones, tens, hundreds = digit(k, 1), digit(k, 2), digit(k, 3)
                if not found(checker, ones, tens, hundreds):
                    print(j, k, i)

#only difference is to use digit2 rather than digit1
def tester2():
    candidates = numbers_divisible_by_3()
    for i in candidates:
        checker = reset_checker()
        ones, tens, hundreds = digit2(i)
        if not found(checker, ones, tens, hundreds):
            j = i // 3
            ones, tens, hundreds = digit2(j)
            if not found(checker, ones, tens, hundreds):
                k = 2 * j
                ones, tens, hundreds = digit2(k)
                if not found(checker, ones, tens, hundreds):
                    print(j, k, i)

if __name__ == '__main__':
    print('run tester1:')
    tester1()
    print('run tester2:')
    tester2()
