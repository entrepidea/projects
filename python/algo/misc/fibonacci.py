"""
This code implements fibonacci sequence.

author: Aaron Yee
Date: 01/16/25

"""

result=[]
for i in range(1,100001):

     if i==1 or i==2:
         result.append(i)
     else:
         x = result[i-2]+result[i-3]
         result.append(x)

l = len(result)
last_ele = result[l-1]
sec_last_ele = result[l-2]
print(last_ele/sec_last_ele)