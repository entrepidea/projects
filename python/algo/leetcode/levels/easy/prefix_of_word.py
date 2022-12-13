"""
Given a sentence that consists of some words separated by a single space, and a searchWord, check if searchWord is a prefix of any word in sentence.

Return the index of the word in sentence (1-indexed) where searchWord is a prefix of this word. If searchWord is a prefix of more than one word, return the index of the first word (minimum index). If there is no such word return -1.

A prefix of a string s is any leading contiguous substring of s.

https://leetcode.com/problems/check-if-a-word-occurs-as-a-prefix-of-any-word-in-a-sentence/

12/09/22

"""
def prefix(sentence : str, searchWord : str)->int:
	words = sentence.split(' ')
	print(words, end='\n')
	cnt = 1
	for w in words:
		if w.startswith(searchWord):
			return cnt
		cnt += 1

	return -1

if __name__ == '__main__':
	searchWord = 'burg'
	sentence = 'i love eating burger'
	print(prefix(sentence, searchWord))
	sentence = "this problem is an easy problem"
	searchWord = "pro"
	print(prefix(sentence, searchWord))
	sentence = "i am tired"
	searchWord = "you"
	print(prefix(sentence, searchWord))


