"""
A sentence is a list of words that are separated by a single space with no leading or trailing spaces. Each word consists of lowercase and uppercase English letters.

A sentence can be shuffled by appending the 1-indexed word position to each word then rearranging the words in the sentence.

For example, the sentence "This is a sentence" can be shuffled as "sentence4 a3 is2 This1" or "is2 sentence4 This1 a3".
Given a shuffled sentence s containing no more than 9 words, reconstruct and return the original sentence.

https://leetcode.com/problems/sorting-the-sentence/

06/24/23

"""

def foo(s:str)->str:
	a = s.split(' ')
	r = [None]*len(a)
	for w in a:
		idx, word = int(w[-1:]),w[:-1]
		r[idx-1] = word
	
	return ' '.join(r)

if __name__ == '__main__':
	s = 'is2 sentence4 This1 a3'
	print(foo(s))
	s = 'Myself2 Me1 I4 and3'
	print(foo(s))
	
