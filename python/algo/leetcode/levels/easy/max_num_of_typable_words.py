"""
There is a malfunctioning keyboard where some letter keys do not work. All other keys on the keyboard work properly.

Given a string text of words separated by a single space (no leading or trailing spaces) and a string brokenLetters of all distinct letter keys that are broken, return the number of words in text you can fully type using this keyboard.

https://leetcode.com/problems/maximum-number-of-words-you-can-type/

09/17/22

"""

def typable_words_number(texts: str, brkn_letters: str) -> int:
	txt_arr = texts.split(' ')
	s1 = set(txt_arr)
	print(s1)
	s2 = set(list(brkn_letters))
	print(s2)

	cnt = 0
	for t in s1:
		s = set(list(t))
		if s.intersection(s2):
			print(f'found! {s} share with {s2}')
			continue
		else:
			print(f' Not found! {s} doesn\'t share with {s2}')
			cnt += 1
			
	
	return cnt

if __name__ == '__main__':
	texts = 'hello world'
	brkn_letters = 'ad'
	print(typable_words_number(texts, brkn_letters))

	texts = "leet code"
	brkn_letters = "e"
	print(typable_words_number(texts, brkn_letters))
	


