
#From Udemy course, Taming big data with Spark and python
#https://www.udemy.com/taming-big-data-with-apache-spark-hands-on/learn/v4/content

from pyspark import SparkConf, SparkContext
import collections

conf = SparkConf().setMaster("local").setAppName("RatingsHistogram")
sc = SparkContext(conf = conf)

lines = sc.textFile("file:///media/sf_projects/github/python/tutorials/bigdata/u.data")
ratings = lines.map(lambda x: x.split()[2])
result = ratings.countByValue()

sortedResults = collections.OrderedDict(sorted(result.items()))
for key, value in sortedResults.items():
    print("%s %i" % (key, value))
