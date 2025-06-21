from algo.leetcode.levels.easy.to_lower import to_lower


class Student(object):
    def __init__(self, name, age, gender):
        self.name = name
        self.age = age
        self.gender = gender

    def __str__(self):
        return f"My name is {self.name} and my age is {self.age} and I am {to_lower(self.gender)}."


s = Student("Aaron", 10, "Male")

print(s)
