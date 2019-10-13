'''
This program is from the udemy, Apache Spark Python #18

measure how popular a movie is. Final result would be map of key:value=movie name: watched_times

key point: broadcast.

data source is from: http://files.grouplens.org/datasets/movielens/ml-100k/

'''
from pyspark import SparkContext, SparkConf

def movie_names():
    movie_map = {}
    with open ('ml-100k/u.item',encoding = "ISO-8859-1") as file:
        for line in file:
            fields = line.split('|')
            movie_map[int(fields[0])] = fields[1]
    return movie_map


def main():
    sf = SparkConf().setMaster("local").setAppName("popular-movies")
    sc = SparkContext(conf = sf)

    name_dict = sc.broadcast(movie_names())

    lines = sc.textFile('file:///media/sf_projects/github/python/tutorials/bigdata/u.data')
    movies_map = lines.map(lambda x : (x.split()[1],1)) #key:value = movie id : 1
    popular_movies = movies_map.reduceByKey(lambda x,y :x+y) #aggregate: count the numbers of a movie. key:value=movie_id:watched_times
    sorted_popular_movies_flipped = popular_movies.map(lambda x:(x[1],x[0])).sortByKey() #key:value=watched_times:movie_id

    sortedMoviesWithNames = sorted_popular_movies_flipped.map(lambda x:(name_dict.value[int(x[1])],x[0]))
    results = sortedMoviesWithNames.collect()
    for result in results:
        movie_name = str(result[0])
        watched_times = str(result[1])
        print(movie_name+ ": "+watched_times)

if __name__ =='__main__':
    main()