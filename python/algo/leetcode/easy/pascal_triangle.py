"""
pascal triangle
https://leetcode.com/problems/pascals-triangle/

date: 10/11/21

"""
def pascal(num):
    ret = []
    for i in range(0, num):
        ret.append([0]*(i+1))
        if i == 0:
            ret[i][0] = 1
        else:
            ret[i][0] = ret[i][i] = 1
            for j in range(1,i):
                ret[i][j] = ret[i-1][j-1]+ret[i-1][j]
            
    return ret

if __name__ == '__main__':
    ret = pascal(6)
    print(ret)
