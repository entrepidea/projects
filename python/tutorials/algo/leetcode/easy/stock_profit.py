"""
buy one stock and sell it later, find out the best time of buying&selling for maximum profit
https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

date: 10/11/21

note: this article (Chinese) explains the thinking process:

https://mp.weixin.qq.com/s?__biz=MzUyNjQxNjYyMg==&mid=2247485288&idx=1&sn=fd043fc723f38bcaecc90d9945981f8a&chksm=fa0e68e9cd79e1ffd965205bb06b1731539bf2e0bbc5991664f5d1d9721b346ec08c85bb9042&scene=21#wechat_redirect

it also expand to cover more complex scenarios.

"""
#this algo is constrainted by one buy and one sell.
def profit(arr):
    buy = -1*arr[0]
    sell = 0
    for i in range(1,len(arr)):
        buy = max(buy, -1*arr[i])
        sell = max(sell,arr[i]+buy)
    return sell

if __name__ =='__main__':
    arr = [7,1,5,3,6,4]
    print(profit(arr))
    arr = [7,6,4,3,1]
    print(profit(arr))
