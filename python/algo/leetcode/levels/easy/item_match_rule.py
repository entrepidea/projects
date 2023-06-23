"""
You are given an array items, where each items[i] = [typei, colori, namei] describes the type, color, and name of the ith item. You are also given a rule represented by two strings, ruleKey and ruleValue.

The ith item is said to match the rule if one of the following is true:

ruleKey == "type" and ruleValue == typei.
ruleKey == "color" and ruleValue == colori.
ruleKey == "name" and ruleValue == namei.
Return the number of items that match the given rule.

https://leetcode.com/problems/count-items-matching-a-rule/

06/17/23

"""

def foo(items:[[int]],key:str,val:str)->[int]:
	dic = {'type':0,'color':1,'name':2}
	idx = dic[key]
	cnt=0
	for item in items:
		if item[idx] == val:
			cnt+=1
	return cnt

if __name__ == '__main__':
	items = [["phone","blue","pixel"],["computer","silver","lenovo"],["phone","gold","iphone"]]
	ruleKey = "color"
	ruleValue = "silver"
	print(foo(items,ruleKey,ruleValue))
	items = [["phone","blue","pixel"],["computer","silver","phone"],["phone","gold","iphone"]]
	ruleKey = "type"
	ruleValue = "phone"
	print(foo(items,ruleKey,ruleValue))

