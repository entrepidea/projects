from abc import abstractclassmethod, ABCMeta

"""
Interface or abstract class in Python can be used for type checking, but it's advised not to overuse it, since Python is 
not a traditional strong type checking language. Overusing it might lead to reduced flexibility

12/20/20

"""


class IStream(metaclass=ABCMeta):
    @abstractclassmethod
    def read(cls, maxbyets=-1):
        pass

    def write(self, data):
        pass
