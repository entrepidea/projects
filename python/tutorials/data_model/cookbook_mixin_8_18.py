"""

minxin is used to extend class functionality, to the effect of multi-inheritence.
mixin class normally doesn't have state thus no __init__
it doesn't make sense to instantiate a mixin class either
an alternative to mixin is decorator

01/01/2021

"""

class LoggedMappingMixin:
    __slots__ = ()

    def __getitem__(self, item):
        print("get item: " + str(item))
        return super().__getitem__(item)

    def __setitem__(self, key, value):
        print("set item {} for {!r}".format(key, value))
        return super().__setitem__(key, value)

    def __delitem__(self, item):
        print("delete item: " + str(item))
        return super().__delitem__(item)

class SetOnceMappingMixin:
    __slots__ = ()

    def __setitem__(self, key, value):
        if key in self:
            raise TypeError("{} has been set".format(str(key)))
        return super().__setitem__(key, value)

class StringkeyMappingMixin:
    __slots__ = ()

    def __setitem__(self, key, value):
        if not isinstance(key, str):
            raise TypeError("key {} must be a string type!".format(key))
        return super().__setitem__(key,value)

"""

Using decorator has the same effect as mixin, and multi-inheritence is no longer involved.

"""
def LoggedMapping2(cls):
    cls_getitem = cls.__getitem__
    cls_setitem = cls.__setitem__
    cls_delitem = cls.__delitem__

    def __getitem__(self, key):
        print("get item: " + str(key))
        return cls_getitem(self,key)

    def __setitem__(self, key, value):
        print("set {} = {!r}".format(key, value))
        return cls_setitem(self, key, value)

    def _delitem__(self, key):
        print("delete {}".format(key))
        return cls_delitem(self, key)


