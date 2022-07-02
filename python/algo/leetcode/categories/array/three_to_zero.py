"""
find out three elements in an array whose sum equals 0
https://leetcode.cn/problems/3sum/

Date: 07/02/22

"""

def sum_of_three(arr)->[]:
    neg = [i for i in arr if i <0]
    neg.sort()
    pos = [i for i in arr if i >= 0]
    pos.sort()

    #print(f'neg={neg},pos={pos}')
    
    ret = []
    for i in range(len(neg)-1):
        s = neg[i] + neg[i+1]
        for j in range(len(pos)):
            if s == -1*pos[j]:
                found = [neg[i],neg[i+1],pos[j]]
                ret.append(found)
                break

    for i in range(len(pos)-1):
        s = pos[i] + pos[i+1]
        for j in range(len(neg)):
            if s == -1*neg[j]:
                found = [pos[i],pos[i+1],neg[j]]
                ret.append(found)
                break
    return ret

if __name__ == '__main__':
    arr = [-1,0,1,2,-1,-4]
    print(arr)
    print(sum_of_three(arr))
