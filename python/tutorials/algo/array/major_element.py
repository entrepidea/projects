"""
@Desc: Leetcode - Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.
@Source: https://leetcode.com/problems/majority-element/
@Date: 10/30/19

"""

def foo(arr):
    dic = {k:0 for k in set(arr)}

    for k in arr:
        dic[k] = dic[k]+1
        if dic[k]>len(arr)/2:
            return k

    return None

def test_foo():
    assert foo([3,2,3]) == 3
    assert foo([2,2,1,1,1,2,2]) == 2
    assert foo([1, 2, 3, 4, 5, 6, 2]) is None


def foo2(arr):
    l = sorted(arr)
    threshold = int(len(l)/2+1)
    count = 0
    i=0
    while i < len(l):
        num = l[i]
        j = i+1
        count = 1
        while j<len(l) and l[j]==num:
            j = j+1
            count = count+1
        if count == threshold:
            return num
        i=j
    return None

def test_foo2():
    assert foo2([3,2,3]) == 3
    assert foo2([2,2,1,1,1,2,2]) == 2
    assert foo2([1, 4, 3, 4, 5, 6, 2]) is None
    assert foo2([1, 4, 4, 4, 5, 4, 2]) ==4
