"""
You are given a string s of even length. Split this string into two halves of equal lengths, and let a be the first half and b be the second half.

Two strings are alike if they have the same number of vowels ('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'). Notice that s contains uppercase and lowercase letters.

Return true if a and b are alike. Otherwise, return false.

https://leetcode.com/problems/determine-if-string-halves-are-alike/

06/15/23

"""

def foo(s:str)->bool:
	m = len(s)//2
	a,b = s[:m],s[m:]
	print(f'a={a},b={b}')
	vowels = ['a','e','i','o','u','A','E','I','O','U']
	n1=0
	for c in a:
		if c in vowels:
			n1+=1
	n2=0
	for c in b:
		if c in vowels:
			n2+=1
	return n1==n2

if __name__ == '__main__':
	s = 'book'
	print(foo(s))
	s = 'textbook'
	print(foo(s))
