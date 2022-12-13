"""
Given a valid (IPv4) IP address, return a defanged version of that IP address.

A defanged IP address replaces every period "." with "[.]".

https://leetcode.com/problems/defanging-an-ip-address/

09/04/22

"""

def defang(address : str) -> str:
	defang_addr = ''
	for ch in address:
		if ch == '.':
			defang_addr = defang_addr + '[.]'
		else:
			defang_addr = defang_addr + ch

	return defang_addr

if __name__ == '__main__':
	s = '1.1.1.1'
	print(defang(s))
