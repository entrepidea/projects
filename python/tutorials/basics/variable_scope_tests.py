g_var = 10

#when trying to update a global variable in a func, use "global" keyword
def foo():
    global  g_var
    print(g_var)
    g_var +=10
    print(g_var)


foo()

print(g_var)