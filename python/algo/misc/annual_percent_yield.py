"""
APY is 5%, deposit $100, 10 year compound rate

Aaron Yee

01/27/25

"""
percent_increase=5
money=100
years_of_compound_rate=10
import math
for i in range(1,years_of_compound_rate+1):
    money*=percent_increase/100+1
cents=round((round(money,2)-math.floor(money))*100,2)
print(f'You have {math.floor(money)} dollars and {int(cents)} cents.')