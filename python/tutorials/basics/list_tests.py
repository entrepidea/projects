#this is to test the basic type: List
#Notes: the elements in a list can be of different types

#test 1): in-place reverse a list
def reverse_list(src):
    last = len(src) - 1
    for x in range(0, int(len(src)/2),1):
        src[x],src[last-x] = src[last-x],src[x]

    print(src)

def main():
    reverse_list([1,2,3,4,5,6])

if __name__ == "__main__":
    main()