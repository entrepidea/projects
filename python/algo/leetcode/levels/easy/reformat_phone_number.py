"""
You are given a phone number as a string number. number consists of digits, spaces ' ', and/or dashes '-'.

You would like to reformat the phone number in a certain manner. Firstly, remove all spaces and dashes. Then, group the digits from left to right into blocks of length 3 until there are 4 or fewer digits. The final digits are then grouped as follows:

2 digits: A single block of length 2.
3 digits: A single block of length 3.
4 digits: Two blocks of length 2 each.
The blocks are then joined by dashes. Notice that the reformatting process should never produce any blocks of length 1 and produce at most two blocks of length 2.

Return the phone number after formatting.

https://leetcode.com/problems/reformat-phone-number/

TODO: not completed yet.

05/27/23

"""
import re
def foo(s : str)->str:
	s = s.replace(' ' ,'').replace('-','')
	n,i = len(s),0
	ret = ''
	while n>0:
		if n == 4:
			print(f'n={n},i={i},j={j}')
			ret += s[i:i+2]+'-'+s[i+2:]
			return ret
		else:
			j = i+3
			print(f'i={i},j={j},n={n}')
			ret += s[i:j]
			if j<n:
				print(f'j={j}')
				ret+='-'
			i = j
			n -=3	
			
	return ret

#most elegant
#https://leetcode.com/problems/reformat-phone-number/solutions/979806/1-liner-python-java-c/
def foo2(s:str)->str:
	return re.sub('(...?(?=..))', r'\1-', re.sub('\D', '', s))

if __name__ == '__main__':
	phone_num = '1-23-45 6'
	print(foo2(phone_num))
	phone_num = '123 4-567'
	print(foo2(phone_num))
	phone_num = '123 4-567 8'
	print(foo2(phone_num))
