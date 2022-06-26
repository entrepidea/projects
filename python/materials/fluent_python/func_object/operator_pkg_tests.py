"""

operator is a convinient module

"""

import operator
from operator import itemgetter

def operator_names():
    [print (name) for name in dir(operator) if not name.startswith('_')]

def item_getter():
    countries = [
        ('Toyko','JP'),
        ('Mexico City','MX'),
        ('Dedhi', 'IN'),
        ('Beijing','CN'),
        ('Washington','USA'),
        ('SanPaul', 'BR')
    ]

    [print(country) for country in sorted(countries, key=itemgetter(1)) ]

def main():
    operator_names()

if __name__ == '__main__':
    main()