import math
def binomial_coefficient(n, k):
    return int(math.factorial(n)/(math.factorial(k)*math.factorial(n-k)))
print(binomial_coefficient(10, 5))