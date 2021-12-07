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

def jacobiSymbol(a, n):
    # Defined only for such a and n that comply with requirements
    assert((n > a > 0) and (n % 2 == 1))
    t = 1
    # Using the properties of Jacobi Symbol we calculate it
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
    # Generate random odd number in range [2^(s-1), 2^2)
    n = randomOddNumber(math.pow(2, s-1), math.pow(2, s))
    # Generate random number in range [2, n-1)
    a = random.randint(2, n-1)
    # Calculate Jacobi Symbol
    x = jacobiSymbol(a, n)
    # If Jacobi Symbol is 0, number is composite
    if (x == 0):
        return False, n
    y = gmpy2.powmod(gmpy2.mpz(a), gmpy2.mpz((n-1)/2), gmpy2.mpz(n))
    if ((x % n) != y):
        return False, n

    # If number surpasses above requirements, number is prime
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

            # SS Primality test thinks it is prime
            if prime:
                # +1 to the number of times SS Primality test thinks it evaluted a prime number
                prob_prime += 1
                # Check if the number is really prime
                if isPrime(n):
                    # +1 to the number of times SS Primality assumed correctly when it evaluted a prime number
                    real_prime += 1
                # We are plotting an error probabilty, so (error = 1 - success)
                r = 1 - real_prime/prob_prime
                x_axis.append(prob_prime)
                if error_prob != 0:
                    # Plot average of error
                    y_axis.append(average(r, error_prob))
                else:
                    y_axis.append(r)
                error_prob = r 

    # Error probability plot 
    plt.plot(x_axis, y_axis)
    plt.ylabel('Error Probability SS')
    plt.xlabel('Numbers of checks')
    plt.title('Estimation (average-case) for error probability of SS primality test')
    plt.show()
