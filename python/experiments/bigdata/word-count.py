'''
The problem is from udemy's online video: Apache Spark Python
From #section 15.

This program reads a book, pick up each word inside it, and list the words followed by the word's sorted appearance frequencies

'''

from pyspark import SparkContext, SparkConf
import re

def normalize(text):
    return re.compile(r'\W+', re.UNICODE).split(text.lower())



def main():
    sf = SparkConf().setMaster('local').setAppName('word-count')
    sc = SparkContext(conf = sf)

    lines = sc.textFile('file:///media/sf_projects/github/python/tutorials/bigdata/book.txt')
    words = lines.flatMap(normalize)
    '''
    wordsByCount = words.countByValue()
    for word, count in wordsByCount.items():
        cleanWord = word.encode('ascii', 'ignore')
        if (cleanWord):
            print(cleanWord.decode() + " " + str(count))
    '''

    word_counts = words.map(lambda x: (x,1)).reduceByKey(lambda x, y : x+y)
    word_counts_sorted = word_counts.map(lambda x : (x[1],x[0])).sortByKey()
    results = word_counts_sorted.collect()
    for result in results:
        count = str(result[0])
        clean_word = result[1].encode('ascii','ignore')
        if(clean_word):
            print(clean_word.decode()+":\t\t"+count)


if __name__ == '__main__':
    main()