"""
NOTE: itertble is an object that implements __iter__, inside which an iterator instance is returned.
an iterator implements __next__ and __iter__, in the latter method, the iterator itself is returned. 

initial draft: 2/2/24
last update: 2/2/24

"""
import re
import reprlib
RE_WORD = re.compile('\w+')
class Sentence:
    def __init__(self, text):
        self.text = text
        self.words = RE_WORD.findall(text)
    def __getitem__(self,index):
        return self.words[index]
    def __len__(self):
        return len(self.words)
    def __repr__(self):
        return 'Sentence(%s)'%reprlib.repr(self.text)
        
class Test:
    pass

class Sentence2:
    def __init__(self,text):
        self.text = text
        self.words = RE_WORD.findall(text)
    
    def __repr__(self):
        return 'Sentence(%s)'%reprlib.repr(self.text)
        
    def __iter__(self):
        return SentenceIterator(self.words)
        
class SentenceIterator:
    def __init__(self,words):
        self.words = words
        self.index = 0
    def __next__(self):
        try:
            word = self.words[sef.index]
        except IndexError:
            raise StopIteration()
        self.index += 1
        return word
    def __iter__(self):
        return self

#the yield keyword makes the enclosing function a generator.
#a generator is an iterator that produce values passed to yield
class Sentence3:
    def __init__(self, text):
        self.text = text
        self.words = RE_WORD.findall(text)
    def __repr__(self):
        return 'Sentence(%s)'%reprlib.repr(self.text)
    def __iter__(self):
        for word in self.words:
            yield word
            
#lazy Sentece - defer producing values as late as many as needed. No eager to create word list, save time and space (memory)
class Sentence4:
    def __init__(self, text):
        self.text = text
    def __repr__(self):
        return 'Sentence(%s)'%reprlib.repr(self.text)
    def __iter__(self):
        for match in RE_WORD.finditer(self.text):
            yield match.group()
            




																		
