"""
Property inheritance is to extend the functionality of properties of the base class

"""


class Person:
    def __init__(self, name):
        self.name = name

    @property
    def name(self):
        return self._name

    # setter
    @name.setter
    def name(self, value):
        if not isinstance(value, str):
            raise TypeError('Expected a string')
        self._name = value

    # deleter
    @name.deleter
    def name(self):
        raise AttributeError('Can\'t delete attribute')


# example of a class inherit from Person and extends the name property with new functionality
class SubPerson(Person):
    @property
    def name(self):
        print('Getting name')
        return super().name

    @name.setter
    def name(self, value):
        print('Setting name to ', value)
        super(SubPerson, SubPerson).name.__set__(self, value)

    @name.deleter
    def name(self):
        print('Deleting name')
        super(SubPerson, SubPerson).name.__delete__(self)


s = SubPerson('Guido')
print(s.name)
s.name = 42  # error raised!
s.name = 'John'
print(s.name)
