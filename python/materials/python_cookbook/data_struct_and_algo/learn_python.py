x=13
quotient=int(x/3)
rem=x%3
print(f"{x} divided by 3 is equal to {quotient} with remainder {rem}.")
print(type(x))
x+=0.5
y="hello world"
print(type(y))
z=["apple", "banana", "cherry"]
print(type(z))
a = ("apple", "banana", "cherry")
print(type(a))
table={(1,2):"tuple_key1", (3,4):"tuple_key2", (5,6):"tuple_key3"}
print(type(x))
print(table)
print(type(table))
print(table.keys())
print(table.values())
b=True
print(type(b))
print(int(35.88))
txt = " Hello World "
c = txt.strip()
print(txt)
print(c)
print(bool("abc"))
print(bool([]))        # False (empty list)
print(bool([1, 2, 3]))  # True (non-empty list)
print(bool(0))         # False (zero)
print(bool(42))        # True (non-zero number)
fruits = ["apple", "banana"]
if "apple" in fruits:
 print("Yes, apple is a fruit!")
this_list = ['apple', 'banana', 'cherry']
print(len(this_list))
print(this_list.pop())
print(this_list.pop())
print(this_list.pop())
print(this_list.clear())
if this_list.clear():
 print("Has items")
else:
 print("Summoning Black Hole")
[print(x) for x in ['apple', 'banana', 'cherry']]
this_list = ['apple', 'banana', 'cherry']
new_list = [x for x in fruits if x == 'banana']
print(new_list)
new_list = ['apple' for x in this_list]
print(new_list)
list1=[3,1,7,2,6,4,5,8,0,9]
print(sorted(list1))
list1=[3,1,7,2,6,4,5,8,0,9]
print(sorted(list1, reverse=True))
print(list1.reverse())
print(list1.sort(reverse = True))
list1=[3,1,7,2,6,4,5,8,0,9]
list2 = list1.copy()
print(list2)
list2 = list(list1)
print(list2)
list3 = list1 + list2
print(list3)
print(list1.extend(list2))
list1 = ['a', 'b' , 'c']
list2 = [1, 2, 3]
for x in list2:
  list1.append(x)
print(list1)
fruits = ("apple", "banana", "cherry", "orange", "kiwi", "melon", "mango")
print(fruits[2:5])
my_tuple=(3,6,32,5,2,56,25432)
del my_tuple
#print(my_tuple) Not defined
set1={"apple", "banana", "cherry"}
print(set1-{"banana"})
print("itis")
set1 = {"a", "b", "c"}
set2 = {1, 2, 3}
set3 = {"John", "Elena"}
set4 = {"apple", "bananas", "cherry"}
my_set = set1.union(set2, set3, set4)
print(my_set)