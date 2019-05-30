from functools import reduce
def process(file_name):
    with open(file_name,'r') as f:
        contents = f.readlines()
    incomes = [float(amt) for _, _, _, amt, _, _, _, _ in (line.split(',') for line in contents[1:])]
    return reduce(lambda x,y: x+y, incomes)




