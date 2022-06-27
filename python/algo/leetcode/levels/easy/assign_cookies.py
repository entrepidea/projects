"""
Assume you are an awesome parent and want to give your children some cookies. But, you should give each child at most one cookie.
Each child i has a greed factor g[i], which is the minimum size of a cookie that the child will be content with; and each cookie j has a size s[j]. If s[j] >= g[i], we can assign the cookie j to the child i, and the child i will be content. Your goal is to maximize the number of your content children and output the maximum number.
https://leetcode.com/problems/assign-cookies/

Date: 10/31/21

"""
def assign(g,s)->int:
    g.sort()
    s.sort()
    s1 = []
    for e in s: s1.append([e,False])
    
    print(s1)
    count = 0
    for e in g:
        for x in s1:
            if e <= x[0] and x[1] == False:
                x[1] = True
                count += 1
                break
    return count

if __name__ == '__main__':
    g = [1,2,3]
    s = [1,1]
    print(assign(g,s))
    g = [1,2]
    s = [1,2,3]
    print(assign(g,s))
