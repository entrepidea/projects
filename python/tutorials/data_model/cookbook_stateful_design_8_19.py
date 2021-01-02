"""

A elegant design of a state machine is to create classes for each state, to avoid a good amount of if-else conditional
statements. A key for the "State" classes is that they don't store any instance variables, or stateless in their own.

01/02/2021

"""

class ConnectionState:
    @staticmethod
    def read(conn):
        raise NotImplementedError()

    @staticmethod
    def write(conn, data):
        raise NotImplementedError()

    @staticmethod
    def close(conn):
        raise NotImplementedError()

    @staticmethod
    def open(conn):
        raise NotImplementedError()

class ClosedConnectionState(ConnectionState):
    @staticmethod
    def read(conn):
        raise RuntimeError("Not opened")

    @staticmethod
    def write(conn, data):
        raise RuntimeError("Not opened")

    @staticmethod
    def open(conn):
        conn.new_state(OpenedConnectionState)

    @staticmethod
    def close(conn):
        raise RuntimeError("Already closed")

class OpenedConnectionState(ConnectionState):
    @staticmethod
    def read(conn):
        print("read")

    @staticmethod
    def write(conn, data):
        print("write")

    @staticmethod
    def open(conn):
        raise RuntimeError("Already open")

    @staticmethod
    def close(conn):
        conn.new_state(ClosedConnectionState)

class Connection:
    def __init__(self):
        self.new_state(ClosedConnectionState)

    def new_state(self, connection_state):
        self._state = connection_state

    # delegate to ConnectionState class
    def read(self):
        return self._state.read(self)

    def write(self, data):
        return self._state.write(self, data)

    def open(self):
        return self._state.open(self)

    def close(self):
        return self._state.close(self)


