"""

"""

def foo(s:str)->bool:
	l = list(s)
	for i in range(len(l)):
		if l[i] == '1' and l[i] == l[i+1]:
			return True
	return False

if __name__ == '__main__':
	#s = '1001'
	#print(foo(s))
	s = '110'
	print(foo(s))
