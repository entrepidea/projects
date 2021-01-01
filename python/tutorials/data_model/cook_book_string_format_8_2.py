from sys import argv

_format = {
    'ymd': '{d.year}-{d.month}-{d.day}',
    'mdy': '{d.month}/{d.day}/{d.year}',
    'dmy': '{d.day}/{d.month}/{d.year}'
}


class Date:
    def __init__(self, year, month, day):
        self.year = year
        self.month = month
        self.day = day

    def __format__(self, code):
        if code == '':
            code = 'ymd'
        fmt = _format[code]
        return fmt.format(d=self)


def main(argv):
    d = Date(2020, 12, 21)
    print(format(d))
    print(format(d, 'mdy'))


if __name__ == '__main__':
    main(argv[1:])
