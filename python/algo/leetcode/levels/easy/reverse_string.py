"""
reverse a string
https://leetcode.com/problems/reverse-string/

Date: 10/29/21

"""
def reverse(s) -> str:
	l = list(s)
	le = len(l)
	for i in range(0, le//2):
		l[i],l[le-1-i] = l[le-1-i],l[i]
	
	return ''.join(e for e in l)

if __name__ == '__main__':
	s = 'hello'
	print(reverse(s))

	s = 'hannah'
	print(reverse(s))

	s = 'Jonathan'
	print(reverse(s))

	s = 'Yee'
	print(reverse(s))
