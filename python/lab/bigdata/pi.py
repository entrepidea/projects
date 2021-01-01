
# from: https://blog.sicara.com/get-started-pyspark-jupyter-guide-tutorial-ae2fe84f594f
import timeit
import findspark
findspark.init()

import pyspark
import random


def inside(p):
  x, y = random.random(), random.random()
  return x*x + y*y < 1

def main():
  sc = pyspark.SparkContext(appName="Pi")
  num_samples = 100000000
  start = timeit.default_timer()

  count = sc.parallelize(range(0, num_samples)).filter(inside).count()
  pi = 4 * count / num_samples
  print(pi)
  sc.stop()

  stop = timeit.default_timer()

  print('Time: ', stop - start)

if __name__ == "__main__":
  main()