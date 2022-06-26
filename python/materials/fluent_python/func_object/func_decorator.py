"""
a function decorator returns an updated version of the function it decorates (called decorated function), or a new function at all.
In the code below target is the decorated function, and deco decorate it but return a totally new function.

source: fluent python p.190

Date: 03/12/22

"""

def deco(func):
    def inner():
        print('running inner()')
    return inner

@deco
def target():
    print('print target()')

if __name__ == '__main__':
    target()

