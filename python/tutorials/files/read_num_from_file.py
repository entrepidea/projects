import glob

total = 0
with open('numbers.txt', 'r') as inp, open('output.txt', 'w') as outp:
   for line in inp:
       try:
           num = float(line)
           total += num
           outp.write(line)
       except ValueError:
           print('{} is not a number!'.format(line))

print('Total of all numbers: {}'.format(total))

