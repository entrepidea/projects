#this is a test to find three 3-digit numbers with a ratio of 1:2:3
#The three numbers pick up their 3 digits from a range of 1 to 9.
#the digits of the numbers can't be duplicated.



for i in range(123,329):

    s = set()

    x=i
    y=2*i
    z=3*i

    x0=int(x/100)
    if x0 ==0 or x0 in s:
        continue
    else:
        s.add(x0)

    x1=int(x/10%10)
    if x1==0 or x1 in s:
        continue
    else:
        s.add(x1)

    x2=x%10
    if x2 ==0 or x2 in s:
        continue
    else:
        s.add(x2)

    y0=int(y/100)
    if y0==0 or y0 in s:
        continue
    else:
        s.add(y0)

    y1=int(y/10%10)
    if y1 ==0 or y1 in s:
        continue
    else:
        s.add(y1)

    y2=y%10
    if y2 ==0 or y2 in s:
        continue
    else:
        s.add(y2)

    z0=int(z/100)
    if z0 ==0 or z0 in s:
        continue
    else:
        s.add(z0)

    z1=int(z/10%10)
    if z1 ==0 or z1 in s:
        continue
    else:
        s.add(z1)

    z2=z%10
    if z2 ==0 or z2 in s:
        continue
    else:
        s.add(z2)

    print x0,x1,x2,',\t',y0,y1,y2,',\t',z0,z1,z2