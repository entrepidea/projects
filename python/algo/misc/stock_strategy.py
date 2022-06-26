'''
use dynamic programming to find out the max profit from buying/selling stocks.

source:https://mp.weixin.qq.com/s?__biz=MzUyNjQxNjYyMg==&mid=2247485288&idx=1&sn=fd043fc723f38bcaecc90d9945981f8a&chksm=fa0e68e9cd79e1ffd965205bb06b1731539bf2e0bbc5991664f5d1d9721b346ec08c85bb9042&scene=21#wechat_redirect

date: 09/30/21

note: TODO - the above article not finished yet. 

'''

#most simple one.
def trade(prices):
    if len(prices) == 1:
        return 0
    buy = -1*prices[0]
    sell = 0
    for i in range(1,len(prices)):
        buy = max(buy, -1*prices[i])
        sell = max(sell, prices[i]+buy)

    return sell

if __name__ == '__main__':
    prices = [7,1,5,3,6,4]
    print(trade(prices))
    prices = [7,6,4,3,1]
    print(trade(prices))



