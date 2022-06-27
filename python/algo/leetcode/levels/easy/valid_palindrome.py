def palindrome(s):
    l = list(s)
    clean_arr = []
    for i in range(0,len(l)):
        if l[i].isalnum():
            clean_arr.append(l[i])
    
    #print(clean_arr)

    for i in range(0,len(clean_arr)):
        if clean_arr[i].lower() != clean_arr[len(clean_arr)-1-i].lower():
            return False

    return True

if __name__ == '__main__':
    s = 'A man, a plan, a canal: Panama'
    print(palindrome(s))
    s = 'race a car'
    print(palindrome(s))

