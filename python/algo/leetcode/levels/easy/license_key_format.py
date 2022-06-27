"""
You are given a license key represented as a string s that consists of only alphanumeric characters and dashes. The string is separated into n + 1 groups by n dashes. You are also given an integer k.

We want to reformat the string s such that each group contains exactly k characters, except for the first group, which could be shorter than k but still must contain at least one character. Furthermore, there must be a dash inserted between two groups, and you should convert all lowercase letters to uppercase.

Return the reformatted license key.

https://leetcode.com/problems/license-key-formatting/

Date: 11/01/21

"""
def to_upper(ch) -> chr:
    rlt = ch if (ord(ch)>=ord('A') and ord(ch)<=ord('Z')) or (ord(ch)>=ord('0') and ord(ch)<=ord('9')) or (ch=='-') else chr(ord(ch)-ord('a') + ord('A'))
    return rlt

def format(s,k):
    l = list(s)
    new_arr = []
    for e in l:
        if e != '-':
            new_arr.append(to_upper(e))
    
    segments = len(new_arr)//k
    remainder = len(new_arr)%k

    rlt = []
    if (remainder>0):
        rlt.append(''.join(str(e) for e in new_arr[:remainder]))
        rlt.append('-')
    
    start_pt = remainder
    end_pt = start_pt + k
    for i in range(segments):
        rlt.append(''.join(str(e) for e in new_arr[start_pt:end_pt]))

        if i< segments-1:
            rlt.append('-')

        start_pt = end_pt 
        end_pt += k
    
    #print(rlt)
    return ''.join(str(e) for e in rlt)

if __name__ == '__main__':
    """
    print(to_upper('b'))
    print(to_upper('x'))
    print(to_upper('n'))
    print(to_upper('C'))
    print(to_upper('1'))
    print(to_upper('-'))
    """ 
    s = "UY76EOR-5F3Z-2e-9-w"
    k = 4
    print(format(s,k))
    s = "5F3Z-2e-9-w"
    print(format(s,k))




