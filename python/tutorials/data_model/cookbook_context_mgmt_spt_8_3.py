from socket import AF_INET, SOCK_STREAM, socket
from functools import partial
"""
In order to take advantage "with" semantics, override  
__enter__
__exit__
"""


class LazyConnection:
    def __init__(self, address, family=AF_INET, type=SOCK_STREAM):
        self.address = address
        self.family = family
        self.type = SOCK_STREAM
        self.sock = type

    def __enter__(self):
        if self.sock is not None:
            raise RuntimeError('Already connected')
        self.sock = socket(self.family, self.type)
        self.sock.connect(self.address)
        return self.sock

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.sock.close()
        self.sock = None


def main():
    conn = LazyConnection('www.python.org', 80)
    with conn as s:
        # conn.__enter__() executes: connection open
        s.send(b'GET /index.html HTTP/1.0\r\n')
        s.send(b'Host: www.python.org\r\n')
        s.send(b'\r\n')
        resp = b''.join(iter(partial(s.recv, 8192), b''))
        # conn.__exit__() executes: connection closed


if __name__ == '__main__':
    main()
