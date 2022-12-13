"""
You are given a 0-indexed string array words, where words[i] consists of lowercase English letters.

In one operation, select any index i such that 0 < i < words.length and words[i - 1] and words[i] are anagrams, and delete words[i] from words. Keep performing this operation as long as you can select an index that satisfies the conditions.

Return words after performing all operations. It can be shown that selecting the indices for each operation in any arbitrary order will lead to the same result.

An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase using all the original letters exactly once. For example, "dacb" is an anagram of "abdc".

https://leetcode.com/problems/find-resultant-array-after-removing-anagrams/

10/22/22

"""

from typing import List
def is_anagram(w1 : List[str], w2 : List[str]) -> bool:
	letters = [0]*26
	for i in range(len(w1)):
		c = ord(w1[i]) - ord('a')
		letters[c] += 1
	
	#print(letters)

	for i in range(len(w2)):
		c = ord(w2[i]) - ord('a')
		letters[c] -= 1

	for letter in letters:
		if letter != 0:
			return False

	return True	


		
def rmv_anagrams(words : List[str]) -> List[str]:
	ret = []
	start = words[0]
	ret.append(start)
	i = 1
	while i < len(words):
		while i< len(words) and is_anagram(start, words[i]):
			i += 1
		if i < len(words):
			start = words[i]
			ret.append(start)
		i +=1 

	return ret

if __name__ == '__main__':
	#print(is_anagram('abba','baba'))
	words =  ["abba","baba","bbaa","cd","cd"]
	print(rmv_anagrams(words))
	words = ["a","b","c","d","e"]
	print(rmv_anagrams(words))
