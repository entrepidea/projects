l_or_g=1
strings=[34,43,37,14,59,25]
highest_rank=strings[0]
for i in strings:
    if l_or_g==1:
        if highest_rank<i:
            highest_rank=i
    else:
        if highest_rank>i:
            highest_rank=i
print(highest_rank)