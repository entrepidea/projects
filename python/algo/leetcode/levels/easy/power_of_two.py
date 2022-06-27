def power(n : int) -> bool:
	while n > 0:

		if n == 1:
			return True

		if n%2 != 0:
			return False 
		else:  
			n = n//2

	return True		
	

if __name__ == '__main__':
	print(power(16))
	print(power(8))
	print(power(4))
	print(power(2))
	print(power(1))
	print(power(13))
