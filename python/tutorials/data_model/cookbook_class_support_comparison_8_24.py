"""

when it comes to implement special methods for comparison, total_ordering from package funtools comes in handy.
It excempts you for tedious toils of writing each and every special method for comparing. All you need is to write
up __eq__ and one of the others.

01/04/2021

"""

from functools import total_ordering

class Room:
    def __init__(self, name, width, length):
        self.name = name
        self.width = width
        self.length = length
        self.square_feet = self.width * self.length

@total_ordering
class House:
    def __init__(self, name, style):
        self.name = name
        self.style = style
        self.rooms = []

    @property
    def living_space_footage(self):
        return sum(r.square_feet for r in self.rooms)

    def add_room(self, room):
        self.rooms.append(room)

    def __str__(self):
        return "{} has living space footage of {}, the style is: {}"\
            .format(self.name, self.living_space_footage, self.style)

    def __eq__(self, other):
        return self.living_space_footage == other.living_space_footage

    def __lt__(self, other):
        return self.living_space_footage < other.living_space_footage

