"""
Given a string s containing only lowercase English letters and the '?' character, convert all the '?' characters into lowercase letters such that the final string does not contain any consecutive repeating characters. You cannot modify the non '?' characters.

It is guaranteed that there are no consecutive repeating characters in the given string except for '?'.

Return the final string after all the conversions (possibly zero) have been made. If there is more than one solution, return any of them. It can be shown that an answer is always possible with the given constraints.


https://leetcode.com/problems/replace-all-s-to-avoid-consecutive-repeating-characters/

01/28/23

"""
def other_than(c1:str,c2:str)->str:
	letters = 'abcdefghijklmnopqrstuvwxyz'
	for l in letters:
		if l != c1 and l!=c2:
			return l
	return ''
 
def foo(l)->str:
	i = 0
	while i < len(l):

		while i<len(l) and l[i]!='?':
			i+=1
		
		start = i	
		while i<len(l) and l[i]=='?':
			i += 1
		end = i
		
		q_arr = l[start:end]
		print(f'start={start}, end={end},q_arr={q_arr}')
		for j in range(start, end):
			if j==0: #handle boundery 
				l[j]='a'
			elif j==end-1 and end<len(l): #handle boundery
				l[j] = other_than(l[j-1],l[j+1])
			else:#normal circumstances
				l[j] = other_than(l[j-1],l[j+1])
		i = end+1
	
	return ''.join(l)
				
if __name__ == '__main__':

	s = '??z?s'
	print(s)
	l = list(s)
	print(foo(l))

	s = 'ubv?w'
	print(s)
	l = list(s)
	print(foo(l))
