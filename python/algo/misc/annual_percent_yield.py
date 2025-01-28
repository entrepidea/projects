"""
APY is 5%, deposit $100, 10 year compound rate

Aaron Yee

01/27/25

"""
import math
money=100
for i in range(1,11):
    money*=1.05
cents=round((round(money,2)-math.floor(money))*100,2)
print(f'You have {math.floor(money)} dollars and {int(cents)} cents.')