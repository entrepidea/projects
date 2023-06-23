"""
For a string sequence, a string word is k-repeating if word concatenated k times is a substring of sequence. The word's maximum k-repeating value is the highest value k where word is k-repeating in sequence. If word is not a substring of sequence, word's maximum k-repeating value is 0.

Given strings sequence and word, return the maximum k-repeating value of word in sequence.

https://leetcode.com/problems/maximum-repeating-substring/

04/03/23

"""
def foo(s : str, word : str)->int:
	if len(word) > len(s):
		return 0
	ans = 0
	k = 1
	while word*k in s:
		k += 1
		ans += 1

	return ans		

if __name__ == '__main__':
	s = 'ababc'
	word = 'ab'
	print(foo(s,word))

	s = 'ababc'
	word = 'ac'
	print(foo(s,word))

	s = 'ababc'
	word = 'ba'
	print(foo(s,word))

