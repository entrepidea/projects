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