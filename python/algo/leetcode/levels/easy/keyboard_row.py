"""
Given an array of strings words, return the words that can be typed using letters of the alphabet on only one row of American keyboard like the image below.
https://leetcode.com/problems/keyboard-row/

Date: 11/02/21

"""
def to_lower(ch) ->chr:
    if ord(ch)>=ord('a') and ord(ch)<=ord('z'):
        return ch
    return chr(ord(ch)-ord('A') + ord('a'))

def key(word) -> bool:
    keyboard =  [list('qwertyuiop'),
                list('asdfghjkl'),
                list('zxcvbnm')]
    w_list = list(word)
    #print(w_list)
    for row in keyboard:
        #print (f'to_lower(w_list[0])={to_lower(w_list[0])}')
        if to_lower(w_list[0]) in row:
            for i in range(1, len(w_list)):
                if to_lower(w_list[i]) not in row:
                    return False
            return True


if __name__ == '__main__':
    words = ["Hello","Alaska","Dad","Peace"]
    print([word for word in words if key(word)==True])
    

