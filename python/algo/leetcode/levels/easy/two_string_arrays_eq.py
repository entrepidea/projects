"""
Given two string arrays word1 and word2, return true if the two arrays represent the same string, and false otherwise.

A string is represented by an array if the array elements concatenated in order forms the string.

https://leetcode.com/problems/check-if-two-string-arrays-are-equivalent/

04/09/23

"""
def foo(words1 : [str], words2 : [str])->bool:
	return ''.join(words1) == ''.join(words2)

if __name__ == '__main__':
	word1 = ["ab", "c"]
	word2 = ["a", "bc"]
	print(foo(word1,word2))

	word1 = ["a", "cb"]
	word2 = ["ab", "c"]
	print(foo(word1,word2))

	word1  = ["abc", "d", "defg"]
	word2 = ["abcddefg"]
	print(foo(word1,word2))
