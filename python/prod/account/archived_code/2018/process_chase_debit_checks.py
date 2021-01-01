from sys import argv

def process(file_name):

    with open(file_name,'r') as f:
        content = f.readlines()

    tuples = [(t[0],t[1],t[2],t[3],t[4],-float(t[5]),t[6]) for t in (line.split(',') for line in (l.strip() for l in content[1:]))]

    return tuples

if __name__ == '__main__':
    tuples = process(argv[1:][0])
    [print("%d===>[bank=%s, source=%s, type=%s, date=%s, desc=%s, amount=%s, catogory=%s]"%(tuples.index(t),t[0],t[1],t[2],t[3],t[4],0-float(t[5]),t[6]))
     for t in tuples]
