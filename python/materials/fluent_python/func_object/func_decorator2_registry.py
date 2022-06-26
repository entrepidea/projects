"""
decorator runs right after the decorated functions are defined.

fluent python p.191

Date: 03/12/22 

"""

registry = []
def register(func):
    print('running register (%s)'%func)
    registry.append(func)
    return func

@register
def f1():
    print('running f1()')

@register
def f2():
    print('running f2()')

@register
def f3():
    print('running f3()')

if __name__ =='__main__':
    print('Running main')
    print('registry -> ', registry)

    f1()
    f2()
    f3()
