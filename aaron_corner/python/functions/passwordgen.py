from wordfreq import top_n_list
import random

frequent = top_n_list('en', 10000)

"""
originally wrote line 19 like this but takes up too many lines
new_arr = []
for w in frequent:
    if len(w)==5 or len(w)==6:
        new_arr.append(w)

print (new_arr)


"""
#pythonic way
fivesixwords = [w for w in frequent if len(w) in (5, 6)]

encodedletters = {
    "a": "@",
    "e": "3",
    "i": "1",
    "o": "0",
    "s": "$",
    "I": "1",
    "S": "$",
    "E": "3"
}


def encode(word):
    upper_word = word.capitalize()
    new_word = ""
    for letter in upper_word:
        if letter not in encodedletters:
            new_word += letter
        elif random.randint(0,1):
            new_word += encodedletters[letter]
        else:
            new_word += letter
    return new_word


if __name__ == '__main__':
    password = encode(random.choice(fivesixwords))+encode(random.choice(fivesixwords))
    print(password)


