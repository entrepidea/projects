"""
This code implements fibonacci sequence.

author: Aaron Yee
Date: 01/16/25

"""

result=[]
for i in range(1,21):

     if i==1 or i==2:
         result.append(i)
     else:
         x = result[i-2]+result[i-3]
         result.append(x)

#print the fibonacci sequence for demo
[print(i) for i in result]

#find the last two elements of the sequence and calculate its ratio
l = len(result)
last_ele = result[l-1]
sec_last_ele = result[l-2]
print(f'Find last two elements [{last_ele},{sec_last_ele}] of the sequence and calculate its ratio:')
print(last_ele/sec_last_ele)