#!/usr/bin/env python

from time import time
from random import randint
from math import gcd
import matplotlib.pyplot as plt

def average(times, step):
    return sum(times)/step

avg = 0
ns = []
timel = []
avg_time = []
step = 1000
sub_step = 0
for n in range(10, 10**5):
    rand1 = randint(2**(n-1), 2**(n)-1)
    rand2 = randint(2**(n-1), 2**(n)-1)
    start = time()
    gcd(rand1, rand2)
    end = time()
    dt = end - start
    timel.append(dt)
    if len(timel) > step:
        sub_step += step
        avg = average(timel, step)
        ns.append(sub_step)
        avg_time.append(avg)
        timel = []
    print("Runs left: " + str(10**5 - n))

plt.plot(ns, avg_time)
plt.xlabel('n')
plt.ylabel('average time')
plt.title('Average running time - Euclidian Algorithm!')
plt.show()
