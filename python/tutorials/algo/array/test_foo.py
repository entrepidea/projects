from unittest import TestCase


def major_element(arr):

    dic = {k:0 for k in set(arr)}
    print(dic)

    """
    [dic[i] = dic[i]+1 for i in arr]
    return 1
    """

def test_major_element():
    major_element([3,2,3])