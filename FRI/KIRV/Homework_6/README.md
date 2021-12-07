# Homework 6

## Problem statement

![image](https://user-images.githubusercontent.com/48418580/145065916-f064b3b0-48d9-490e-abe4-c829b7372ca7.png)

## Solution

Before explaining the procedure I will explain functions I used.
Explanation is done using comments in the code

Average functions, that does not need additional explanation:

    def average(x1, x2):
      return (x1 + x2)/2
      
Random odd number generator (Using an algorithm based on sequencing):

    def randomOddNumber(a,b):
      a = a // 2
      b = b // 2 - 1
      number = random.randint(a,b)
      number = (number * 2) + 1
      assert (number % 2 != 0)
      
      return number
      
Function that calculates [Jacobi symbol](https://en.wikipedia.org/wiki/Jacobi_symbol):

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
          
Reference function to check whether number is really prime:

    # Check if there is any divisor of n in range [2, sqrt(n)]
    def isPrime(n):
        return n > 1 and all(n % i for i in islice(count(2), int(math.sqrt(n) - 1)))

Sollovay-Strassen Primality test function following the lecture slides:

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

Main procedure that uses functions and plots error probability

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

    # Splotaj average uspesnosti da je algorithm dejansko zadel prime, do trenutne cifre
    plt.plot(x_axis, y_axis)
    plt.ylabel('Error Probability SS')
    plt.xlabel('Numbers of checks')
    plt.title('Estimation (average-case) for error probability of SS primality test')
    plt.show()

Plot:

  ![image](https://user-images.githubusercontent.com/48418580/145068573-12382a80-11ce-4003-ba09-9e7b6c577b2d.png)


