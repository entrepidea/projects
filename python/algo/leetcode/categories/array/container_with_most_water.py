"""
You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).

Find two lines that together with the x-axis form a container, such that the container contains the most water.

Return the maximum amount of water a container can store.

https://leetcode.com/problems/container-with-most-water/description/


"""
def area(i:int, j:int, heights:[int])->int:
    h = heights[i] if heights[i]<heights[j] else heights[j]
    w = j-i
    return h*w

#brutal force
def foo(heights:[int])->int:
    r = 0
    for i in range(len(heights)-1):
        for j in range(i+1,len(heights)):
            k = area(i,j, heights)
            if r < k:
                r = k

    return r

#Try to use two-pointers. Set one pointer to the left and one to the right of the array. Always move the pointer that points to the lower line.
def foo2(heights:[int])->int:
    ret = 0
    l,r = 0, len(heights)-1
    while l < r:
        if heights[l] < heights[r]:
            a = heights[l]*(r-l)
            l += 1
        else:
            a = heights[r]*(r-l)
            r -= 1
        if a > ret:
            ret = a
    return ret            

if __name__ == '__main__':
    heights = [1,8,6,2,5,4,8,3,7]
    print(foo2(heights))
    heights = [1,1]
    print(foo2(heights))
