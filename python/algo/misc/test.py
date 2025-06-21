import calendar
import datetime
import nltk
from nltk.corpus import words
nltk.download("words")
english_words = set(words.words())
today = datetime.date.today()
# Get the day of the week as a number (0 = Monday, 6 = Sunday)
day_number = today.weekday()
print (f'Welcome to the JavaPython company.\n\nToday is {calendar.day_name[day_number]} {today}.')
print("The word of the day is...\n")
print(list(english_words)[0])