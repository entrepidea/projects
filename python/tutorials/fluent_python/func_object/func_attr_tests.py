
from inspect import signature

def clip(text, max_len=80):
    """return text clipped at the last space before or after the max_len"""
    end = None
    if len(text) > max_len:
        space_before = text.rfind('',0,max_len)
        if space_before>=0:
            end = space_before
        else:
            space_after = text.rfind('',max_len)
            if space_after>=0:
                end = space_after
    if end is None:
        end = len(text)

    return text[:end].rstrip()



def main():
    print(clip.__defaults__)
    print(clip.__code__)
    print(clip.__code__.co_argcount)

    sig = signature(clip)
    print(sig)
    [print(param.kind, ':', name, '=', param.default) for (name, param) in sig.parameters.items()]

if __name__ == '__main__':
    main()