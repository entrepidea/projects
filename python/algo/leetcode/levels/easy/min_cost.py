"""
A shop is selling candies at a discount. For every two candies sold, the shop gives a third candy for free.

The customer can choose any candy to take away for free as long as the cost of the chosen candy is less than or equal to the minimum cost of the two candies bought.

For example, if there are 4 candies with costs 1, 2, 3, and 4, and the customer buys candies with costs 2 and 3, they can take the candy with cost 1 for free, but not the candy with cost 4.
Given a 0-indexed integer array cost, where cost[i] denotes the cost of the ith candy, return the minimum cost of buying all the candies.

https://leetcode.com/problems/minimum-cost-of-buying-candies-with-discount/

12/31/23

"""


def min_cost(cost):
    s_cost = sorted(cost, reverse=True)
    n = 1 
    rlt = 0
    for x in s_cost:
        if n % 3 != 0:
            rlt += x
        n += 1
    return rlt

if __name__ == '__main__':
    cost = [1,2,3]
    print(min_cost(cost))
    cost = [6,5,7,9,2,2]
    print(min_cost(cost))
