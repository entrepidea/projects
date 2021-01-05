import weakref
"""

In a situation where you want to have only ONE instance if given certain input parameters, a cache can be used.
Following shows how this is achieved.

01/05/21

"""
class Spam:
    def __init__(self, name):
        self.name = name


_spam_ref = weakref.WeakValueDictionary()

def get_spam(name):
    if name not in _spam_ref:
        s = Spam(name)
        _spam_ref[name] = s
    else:
        s = _spam_ref[name]

    return s


# alternative to the above - use a manager for better and clearer management
# pay attention to the class Spam2 and how it prevents being instantiated.
class CachedSpamManager:
    def __init__(self):
        self._cache = weakref.WeakValueDictionary()

    def get_spam(self, name):
        if name not in self._cache:
            s = Spam2._new(name)
            self._cache[name] = s
        else:
            s = self._cache[name]
        return s

class Spam2:
    def __init__(self):
        raise RuntimeError("is not allowed to instantiate the class directly")

    @classmethod
    def _new(cls, name):
        self = cls.__new__(cls)
        self.name = name
        return self