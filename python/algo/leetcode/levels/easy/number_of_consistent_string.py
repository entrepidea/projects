"""
You are given a string allowed consisting of distinct characters and an array of strings words. A string is consistent if all characters in the string appear in the string allowed.

Return the number of consistent strings in the array words.

https://leetcode.com/problems/count-the-number-of-consistent-strings/

05/17/23

"""
def foo(allowed : str, words : [str])->int:
	cnt = 0
	for word in words:
		consistent = True
		for i,v in enumerate(word):
			if v not in allowed:
				consistent = False
				break
		if consistent:
			cnt += 1
		
	return cnt

if __name__ == '__main__':
	allowed = 'ab'
	words = ["ad","bd","aaab","baa","badab"]
	print(foo(allowed,words))

	allowed = "abc" 
	words = ["a","b","c","ab","ac","bc","abc"]
	print(foo(allowed,words))

	allowed = "cad"
	words = ["cc","acd","b","ba","bac","bad","ac","d"]
	print(foo(allowed,words))
