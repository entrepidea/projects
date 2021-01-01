"""
Delegation sometimes is used as alternative to inheritence.
In Python, it's a elegant way to use __getattr__ to delegate functionalities 
to a target instance if the target instance has many methods to be exposed.

One of the main applicable scenarios for delegation pattern is proxying.

12/31/20

"""

class Proxy:
    def __init__(self, obj):
        self._obj = obj

    def __getattr__(self, name):
        print('getattr: ', name)
        return getattr(self._obj, name)

    def __setattr__(self, name, value):
        if name.startswith('_'):
            super().__setattr__(name, value)
        else:            
            print('setattr:', name, value)
            setattr(self._obj, name,value)

    def __delattr__(self, name):
        if name.startswith('_'):
            super().__delattr__(name)
        else:
            delattr(self._obj, name)

class Spam:
    def __init__(self,x):
        self.x = x

    def bar(self, y):
        print('Spam.bar: ', self.x, y)



"""

__getattr__ doesn't apply to special methods such as __len__, for those, explicit override them.

"""
class ListLike:
    def __init__(self):
        self._items = []

    def __getattr__(self, name):
        return getattr(self._items,name)

    def __len__(self):
        return len(self._items)


