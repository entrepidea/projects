"""
find happy number, see the link to see what is happy number:
https://leetcode.com/problems/happy-number/

date: 10/13/21

"""
def happy(num : int) -> bool:

	if num == 1:
		return True

	s = set()

	while True:

		temp, sm = num, 0
		while num > 0:
			#print(f'number = {num}')
			sm += (num%10)**2
			num //= 10

		if sm == 1:
			return True	
		
		if sm in s:
			return False
		
		s.add(sm)

		num = sm

if __name__ =='__main__':
	#print(happy(7))
	for i in range(2,1000):
		if happy(i):
			print(f'{i} is happy')	
