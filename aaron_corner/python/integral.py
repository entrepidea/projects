"""
A simple example of integral is that we set the lower limit to be 0, upper limit to be 10, we added the numbers from 0 to 10.
NOTE: learn the loop.

Date: 11/07/21

"""

def addUp(low, high):
    total = 0
    for i in range(low,high):
        total = total + i

    return total        

if __name__ == '__main__':
    rlt = addUp(0,11)
    print(rlt)
