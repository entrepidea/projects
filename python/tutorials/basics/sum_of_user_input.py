#https://www.ynonperek.com/2017/09/21/python-exercises/
#Write a program that asks the user for a number (integer only) and prints the sum of its digits
#reference:
# Exit while loop by user hitting ENTER key
#https://stackoverflow.com/questions/7255463/exit-while-loop-by-user-hitting-enter-key
#note: raw_input obsolete since python 3.5



import sys

sum = 0
while True:
    i = input("Enter a number: ")    
    if not i:
        break
    print("Your input:", i)
    sum = sum+int(i)
print("While loop has exited, the sum of the entering is: ", sum)
