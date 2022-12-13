"""
You are given a 0-indexed 2D integer array brackets where brackets[i] = [upperi, percenti] means that the ith tax bracket has an upper bound of upperi and is taxed at a rate of percenti. The brackets are sorted by upper bound (i.e. upperi-1 < upperi for 0 < i < brackets.length).

Tax is calculated as follows:

The first upper0 dollars earned are taxed at a rate of percent0.
The next upper1 - upper0 dollars earned are taxed at a rate of percent1.
The next upper2 - upper1 dollars earned are taxed at a rate of percent2.
And so on.
You are given an integer income representing the amount of money you earned. Return the amount of money that you have to pay in taxes. Answers within 10-5 of the actual answer will be accepted.


https://leetcode.com/problems/calculate-amount-paid-in-taxes/

10/24/22

"""

from typing import List
def calc(brackets : List[List[int]], income : int) -> float:
	upper = brackets[0][0]
	pct = brackets[0][1]/100
	m = min(upper,income)
	amnt = m*pct
	income -= m
	i=1
	while income > 0 and i < len(brackets): 
		m = min((brackets[i][0] - brackets[i-1][0]), income) 
		pct = brackets[i][1]
		amnt += m*pct/100
		income -= m
		i += 1

	return amnt

if __name__ == '__main__':
	brackets = [[3,50],[7,10],[12,25]]
	income = 10
	print(calc(brackets, income))	
	brackets = [[1,0],[4,25],[5,50]]
	income = 2
	print(calc(brackets, income))	
	brackets = [[2,50]]
	income = 0
	print(calc(brackets, income))	
