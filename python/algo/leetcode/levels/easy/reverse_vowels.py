"""
reverse vowels of a string
https://leetcode.com/problems/reverse-vowels-of-a-string/

Date: 10/29/21

"""
def reverse(s) -> str:
	l = list(s)
	i = 0
	j = len(l) -1 
	vowels = ['a','e','i','o','u']
	while i<=j:
		if l[i] not in vowels:
			i+=1
		if l[j] not in vowels:
			j-=1

		if l[i] in vowels and l[j] in vowels:
			#print(f'i={i},l[i] = {l[i]}; j={j}, l[j] = {l[j]}')
			l[i],l[j] = l[j],l[i]
			#print(f'i={i},l[i] = {l[i]}; j={j}, l[j] = {l[j]}')
			i += 1 
			j -= 1
	
	return ''.join(e for e in l)

if __name__ =='__main__':
	s = 'hello'
	print(f'original={s}, reversed={reverse(s)}')
	s = 'leetcode'
	print(f'original={s}, reversed={reverse(s)}')
	s = 'wow'
	print(f'original={s}, reversed={reverse(s)}')
	s = 'lwoweitu'
	print(f'original={s}, reversed={reverse(s)}')
