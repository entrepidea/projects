"""
determined if a word is all lowcase, all uppcase or just first letter being Capital.
https://leetcode.com/problems/detect-capital/

NOTE:the below code is borrowed from: https://leetcode.com/problems/detect-capital/discuss/1555379/Python-Simple-Implementation
This question is easy, but the code below showcased the Python built-in support for string munipulation.

Date: 11/05/21

"""
def capital(word: str) -> bool:
    return word.upper() == word or word.lower() == word or word.capitalize() == word

if __name__ == '__main__':
    word = 'USA'
    print(capital(word))
