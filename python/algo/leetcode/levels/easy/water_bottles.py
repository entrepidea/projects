"""
There are numBottles water bottles that are initially full of water. You can exchange numExchange empty water bottles from the market with one full water bottle.

The operation of drinking a full water bottle turns it into an empty bottle.

Given the two integers numBottles and numExchange, return the maximum number of water bottles you can drink.

https://leetcode.com/problems/water-bottles/

01/07/23

"""
def bottle(num_bot:int, ex_bot:int)->int:
	rlt = num_bot
	while num_bot//ex_bot != 0:
		x = num_bot//ex_bot
		y = num_bot%ex_bot
		num_bot=x+y
		rlt += x
	return rlt
	
if __name__ == '__main__':
	num_bot = 9
	ex_bot = 3
	print(bottle(num_bot,ex_bot))
	num_bot = 15
	ex_bot = 4
	print(bottle(num_bot,ex_bot))
	num_bot = 100
	ex_bot = 4
	print(bottle(num_bot,ex_bot))
