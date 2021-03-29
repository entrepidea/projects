def dup_exists(arr):
    return len(arr) != len(set(arr))

def test_if_dup_exists():
    arr = [1,2,3,1]
    assert dup_exists(arr)