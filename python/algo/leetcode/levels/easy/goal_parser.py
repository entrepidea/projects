"""
You own a Goal Parser that can interpret a string command. The command consists of an alphabet of "G", "()" and/or "(al)" in some order. The Goal Parser will interpret "G" as the string "G", "()" as the string "o", and "(al)" as the string "al". The interpreted strings are then concatenated in the original order.

Given the string command, return the Goal Parser's interpretation of command.


https://leetcode.com/problems/goal-parser-interpretation/

05/18/23

"""
def foo(s : str)->str:
	rlt = []
	arr = [c for c in s]
	i = 0
	while i < len(arr):
		if arr[i] == '(' and arr[i+1]==')':
			rlt.append('o')
			i +=2
		elif arr[i]=='(' and arr[i+1]!=')':
			rlt.append('al')
			i+=4
		else:
			rlt.append(arr[i])
			i +=1

	return ''.join(rlt)
		
if __name__ == '__main__':
	command = "G()(al)"
	print(foo(command))
	command = "G()()()()(al)"
	print(foo(command))
	command = "(al)G(al)()()G"
	print(foo(command))
