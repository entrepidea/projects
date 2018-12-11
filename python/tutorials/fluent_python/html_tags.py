"""
chapter 5, first class functions

"From positional to keyword-only parameters"

"""

def tag(name, *content, cls=None, **attrs):
    """generate one or more html tags"""
    if cls is not None:
        attrs['class'] = cls

    if attrs:
        attr_str = ''.join ('%s="%s"'%(attr, val) for attr, val in sorted(attrs.items()))
    else:
        attr_str = ''

    if content:
        return '\n'.join('<%s%s>%s<%s>'%(name, attr_str, c, name) for c in content)
    else:
        return '<%s%s />' % (name, attr_str)


def main():
    print(tag('br'))
    print(tag('p','hello'))
    print(tag('p', 'hello','world'))
    print(tag('p', 'hello', id=33))

if __name__ == '__main__':
    main()

