"""
https://leetcode.com/problems/word-pattern/

Date: 10/29/21

"""
def pattern(p,s) -> bool:
	dic = {}
	l = list(p)
	arr = s.split()

	#test
	print(f'l={l},array={arr}')

	if len(l) != len(arr):
		return False

	for i in range(len(l)):
		if l[i] not in dic:
			dic[l[i]] = arr[i]
		else:
			if dic[l[i]] != arr[i]:
				return False
						 
	return True


if __name__ == '__main__':
	p = 'abba'
	s = 'dog cat cat dog'
	print(pattern(p,s))

	s = 'dog cat cat fish'
	print(pattern(p,s))

	p = 'aaaa'
	s = 'dog cat cat dog'
	print(pattern(p,s))


