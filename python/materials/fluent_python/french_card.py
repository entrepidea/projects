"""
From Fluent Python, chapter 1, "a Pythonic Card Deck"

date: 10/05/21

"""

import collections
from random import choice
Card = collections.namedtuple('Card', ['rank','suit'])

class FrenchDeck:
    
    ranks = [str(n) for n in range(2,11)] + list('JQKA')
    suits = 'Spades Diamonds Clubs Hearts'.split()

    def __init__(self):
        self._cards = [Card(rank,suit) for suit in self.suits for rank in self.ranks]

    def __len__(self):
        return len(self._cards)

    def __getitem__(self, position):
        return self._cards[position]


if __name__ == '__main__':
    french_deck = FrenchDeck()
    print(len(french_deck))

    for card in french_deck:
        print(card, end=' ')

    #for i in range(0, len(french_deck)):
    #    print(choice(french_deck), end=',')

