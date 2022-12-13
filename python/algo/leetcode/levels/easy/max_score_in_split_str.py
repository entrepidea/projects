"""
Given a string s of zeros and ones, return the maximum score after splitting the string into two non-empty substrings (i.e. left substring and right substring).

The score after splitting a string is the number of zeros in the left substring plus the number of ones in the right substring.

https://leetcode.com/problems/maximum-score-after-splitting-a-string/

12/02/22

"""
def score(s :str)->int:
	l = list(s)
	rlt = 0
	for i in range(1,len(l)):
		l1 = l[:i]
		l2 = l[i:]
		#print(f'l1={l1},l2={l2}')
		zero_num = l1.count('0')
		one_num = l2.count('1')
		v = zero_num + one_num
		if v>rlt:
			rlt = v
	return rlt

#this is from https://leetcode.com/problems/maximum-score-after-splitting-a-string/discuss/597716/Java-5-Liner-(One-Pass)
#but the last test seems failed? TODO
def score2(s :str)->int:
	zeros = 0
	ones = 0
	m = 0
	for i in range(len(s)):
		if s[i] == '0':
			zeros += 1
		else:
			ones += 1
		if i!=len(s)-1:
			m = max(zeros-ones,m)

	return m + ones

if __name__ == '__main__':
	s = '011101'
	print(score(s))
	s = "00111"
	print(score(s))
	s = "1111"
	print(score(s))

	s = '011101'
	print(score2(s))
	s = "00111"
	print(score2(s))
	s = "1111"
	print(score2(s))
