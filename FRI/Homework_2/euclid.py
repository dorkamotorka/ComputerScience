#!/usr/bin/env python

from time import time
from random import randint
from math import gcd
import matplotlib.pyplot as plt

def average(x1, x2):
    return (x1 + x2)/2

avg = 0
ns = []
avg_time = []
for n in range(10, 10**5):
    start = time()
    rand1 = randint(2**(n-1), 2**(n)-1)
    rand2 = randint(2**(n-1), 2**(n)-1)
    gcd(rand1, rand2)
    end = time()
    dt = end - start
    avg = average(dt, avg)
    
    ns.append(n)
    avg_time.append(avg)
    print("Runs left: " + str(10**5 - n))

plt.plot(ns, avg_time)
plt.xlabel('n')
plt.ylabel('average time')
plt.title('Average running time - Euclidian Algorithm!')
plt.show()
