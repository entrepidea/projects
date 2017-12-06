#this scripts is created to fetch stock data of a given stock symbol
#the data source is from yahoo finance, e.g http://finance.yahoo.com/d/quotes.csv?s=msft&f=price
#the script is run every 10 minutes
#the fetch data is saved and appended to a file whose name is the symbol name, like msft.dat
#the file format is a csv format.

from urllib.request import urlopen
with urlopen('http://finance.yahoo.com/d/quotes.csv?s=msft&f=price)') as response:

