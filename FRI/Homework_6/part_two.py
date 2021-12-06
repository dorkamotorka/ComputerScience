import random
import math
import gmpy2
from itertools import count, islice
import matplotlib.pyplot as plt

def average(x1, x2):
    return (x1 + x2)/2

# Random Odd Number Generator: Using an algorithm based on sequencing
def randomOddNumber(a,b):
    a = a // 2
    b = b // 2 - 1
    number = random.randint(a,b)
    number = (number * 2) + 1

    assert (number % 2 != 0)

    return number

# Calculates Jacobi Symbol
def jacobiSymbol(a, n):
    assert((n > a > 0) and (n % 2 == 1))
    t = 1
    while (a != 0):
        while (a % 2 == 0):
            a /= 2
            r = n % 8
            if (r == 3) or (r == 5):
                t = -t
        a, n = n, a
        if (a % 4 == n % 4 == 3):
            t = -t
        a %= n
    if (n == 1):
        return t
    else:
        return 0

# Check if there is any divisor of n in range [2, sqrt(n)]
def isPrime(n):
    return n > 1 and all(n % i for i in islice(count(2), int(math.sqrt(n) - 1)))

# Sollovay-Strassen Primality test
def SSPrimalityTest(s):
    n = randomOddNumber(math.pow(2, s-1), math.pow(2, s))
    a = random.randint(2, n-1)
    x = jacobiSymbol(a, n)
    y = gmpy2.powmod(gmpy2.mpz(a), gmpy2.mpz((n-1)/2), gmpy2.mpz(n))
    if (x == 0) or (y != x):
        #print(str(n) + ' is composite!')
        return False, n

    #print(str(n) + ' is prime!')
    return True, n

if __name__ == '__main__':
    prob_prime = 0
    real_prime = 0
    error_prob = 0
    x_axis = []
    y_axis = []
    # Run the algorithm
    for s in range(10, 31):
        for _ in range(100000):
            prime, n = SSPrimalityTest(s)

            # Če algoritm vrne probable prime, preveri če je cifra dejansko prime
            if prime:
                prob_prime += 1
                if isPrime(n):
                    #print("It is really prime")
                    real_prime += 1
                r = 1 - real_prime/prob_prime
                x_axis.append(prob_prime)
                if error_prob != 0:
                    y_axis.append(average(r, error_prob))
                else:
                    y_axis.append(r)
                error_prob = r 

    # Splotaj average uspesnosti da je algorithm dejansko zadel prime, do trenutne cifre
    plt.plot(x_axis, y_axis)
    plt.ylabel('Error Probability SS')
    plt.xlabel('Numbers of checks')
    plt.title('Estimation (average-case) for error probability of SS primality test')
    plt.show()
