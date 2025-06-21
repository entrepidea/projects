import nltk
import time
from nltk.corpus import words
nltk.download('words')
english_words = set(words.words())
start_time = time.time()
print(f"Total words in NLTK corpus: {len(english_words)}")
print(list(english_words)[:235892])
end_time = time.time()
print(f"Execution Time: {end_time - start_time} seconds")