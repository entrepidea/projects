"""
Given a string s, return the length of the longest substring between two equal characters, excluding the two characters. If there is no such substring return -1.

A substring is a contiguous sequence of characters within a string.

https://leetcode.com/problems/largest-substring-between-two-equal-characters/

03/07/23

"""
from collections import Counter
def dis(s : str)->int:
	cnt = []
	c = Counter(s).most_common() #turn the original string into a list of tuple which is a pair of char and its freq.
	l = list(filter(lambda x : x[1]>1,c)) #filter to obtain those only have a freq greater than 1
	print(f'filtered list: {l} ')
	if len(l)==0:
		return -1

	for e in l:
		cnt.append(s.rindex(e[0]) - s.index(e[0])-1)
	return max(cnt) 

if __name__ == '__main__':
	s = 'abbacddddddc'
	print(dis(s))
	s = 'aa'
	print(dis(s))
	s = 'cbzxy'
	print(dis(s))
