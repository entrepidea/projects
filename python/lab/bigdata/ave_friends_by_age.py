from pyspark import SparkConf,SparkContext

def parse_line(line):
    age = int(line.split(',')[2])
    friend_count = int(line.split(',')[3])
    return age, friend_count


def main():
    conf = SparkConf().setMaster("local").setAppName("AverageFriendsByAge")
    sc = SparkContext(conf = conf)
    lines = sc.textFile('file:///media/sf_projects/github/python/tutorials/bigdata/fakefriends.csv')
    total_by_age = lines.map(parse_line).mapValues(lambda x : (x,1)).reduceByKey(lambda x,y: (x[0]+y[0],x[1]+y[1]))
    for age, ave_friends in total_by_age.mapValues(lambda x: x[0]/x[1]).collect():
        print('%i, %i'%(age, int(ave_friends)))

if __name__ == '__main__':
    main()