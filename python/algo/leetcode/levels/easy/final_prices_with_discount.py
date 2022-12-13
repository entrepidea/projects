"""
You are given an integer array prices where prices[i] is the price of the ith item in a shop.

There is a special discount for items in the shop. If you buy the ith item, then you will receive a discount equivalent to prices[j] where j is the minimum index such that j > i and prices[j] <= prices[i]. Otherwise, you will not receive any discount at all.

Return an integer array answer where answer[i] is the final price you will pay for the ith item of the shop, considering the special discount.

https://leetcode.com/problems/final-prices-with-a-special-discount-in-a-shop/

12/10/22

"""
def discount(prices : [int])->[int]:
	dis_prices = []
	discount = False
	for i in range(len(prices)):
		for j in range(i+1, len(prices)):
			if prices[j] <=prices[i]:
				print(f'prices[{i}]={prices[i]}, prices[{j}]={prices[j]}, (prices[{i}]-prices[{j}]={prices[i]-prices[j]})')
				dis_prices.append(prices[i]-prices[j])
				discount = True
				break #jump out the inner loop
		if not discount: dis_prices.append(prices[i])
		else: discount = False #reset	
	return dis_prices
	 
#use stack
#https://leetcode.com/problems/final-prices-with-a-special-discount-in-a-shop/solutions/685390/java-c-python-stack-one-pass/
def discount2(prices : [int]) -> [int]:
	stack = []
	for i, p in enumerate(prices):
		while stack and prices[stack[-1]]>=p:
			prices[stack.pop()] -= p
		stack.append(i)
	return prices

if __name__ == '__main__':
	prices = [8,4,6,2,3]
	print(discount2(prices))
	prices = [1,2,3,4,5]
	print(discount2(prices))
	prices = [10,1,1,6]
	print(discount2(prices))
