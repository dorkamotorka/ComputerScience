import math
from math import ceil
import random
from random import randrange
import hashlib

valid_pairs = [
    (1024, 160),
    (2048, 224),
    (2048, 256),
    (3072, 256)
]

def xxrange(start, stop=None, step=1):
    """
    Return a range iterator without overflow.
    """
    if stop is None:
        stop = start
        start = 0
    try:
        return range(start, stop, step)
    except OverflowError:
        assert step != 0
        op = lt if step > 0 else gt
        def xxr():
            x = start
            while op(x, stop):
                yield x
                x += step
        return xxr()

def millerRabin(n, a = None, trace = False, **kargs):
    """
    Perform the Miller-Rabin primality testing algorithm.
    Returns True if n is determined to be composite.
    Returns False if n is not determined to be composite
    -- i.e., it is a probable prime.
    """
    assert n > 1
    m = n-1
    k = 0
    while m % 2 == 0:
        m //= 2
        k += 1
    if trace:
        print("n-1 = m * 2^k = %d * 2^%d" % (m, k))
    if a is None:
        a = randrange(2, n-1)
    b = pow(a, m, n)
    if trace:
        print("b = %d ^ m mod n = %d" % (a, b))
    if b % n == 1:
        if trace:
            print("b mod n = 1  => probable prime")
        return False
    for i in xxrange(k):
        if (b+1) % n == 0:
            if trace:
                print("b ^ (2^%d) == -1 (mod n)  => probable prime" % i)
            return False
        b = b*b % n
    if trace:
        print("for all i < %d: b ^ (2^i) !== -1 (mod n)  => composite" % k)
    return True

def generate_probable_prime(L, N, seedlen):
    '''
    This method uses an approved hash function
    '''
    # Step 1: Check that the (L,N) is in the list of acceptable pairs
    if (L,N) not in valid_pairs:
        print("INVALID")
        return

    # Step 2
    if seedlen < N:
        print("INVALID")
        return

    # Step 3
    n = ceil(L / outlen) - 1

    # Step 4
    b = L - 1 - (n*outlen)

    while True:
        # Step 5: Get an arbitrary sequence of seedlen bits as the domain_parameter_seed
        dps = random.getrandbits(seedlen)
        #print(dps)

        # Step 6
        m = hashlib.sha256()
        m.update(str(dps).encode('utf-8'))
        U = int(m.hexdigest(), 16) %  (2**(N-1))
        #print(U)

        # Step 7
        q = 2**(N-1) + U + 1 - (U % 2)

        # Step 8+9: Test whether or not q is prime (Return True if composite!)
        if millerRabin(q):
            continue

        #print("%s is probable prime" % str(q))

        # Step 10
        offset = 1

        # Step 11
        for counter in range(4*L - 1):
            V = []
            for j in range(n):
                m = hashlib.sha256()
                m.update(str(dps + offset + j).encode('utf-8'))
                v = int(m.hexdigest(), 16) % (2 ** seedlen)
                V.append(v)
            W = 0
            for a, v in zip(range(n-2), V):
                W += v * 2**(outlen*a)
            W += (V[n-1] % (2**b)) * (2** (n*outlen))
            X = W + 2 ** (L - 1)
            c = X % (2*q)
            p = X - (c - 1)
            if not p < (2 ** (L - 1)):
                if not millerRabin(p):
                    print("p = %s is probable prime" % str(p))
                    #print("VALID")
                    return p, q, dps, counter
            offset += (n + 1)

def validate_probable_primes(p, q, dps, c):
    # Step 1
    L = p.bit_length()

    # Step 2
    N = q.bit_length()

    # Step 3: Check that the (L,N) is in the list of acceptable pairs
    if (L,N) not in valid_pairs:
        print("INVALID - not in valid pairs")
        return

    # Step 4
    if c > (4*L - 1):
        print("INVALID - c < (4*L - 1)")
        return

    # Step 5+6
    seedlen = dps.bit_length()
    if seedlen < N:
        print("INVALID - seedlen < N")
        return

    # Step 7
    m = hashlib.sha256()
    m.update(str(dps).encode('utf-8'))
    U = int(m.hexdigest(), 16) %  (2**(N-1))

    # Step 8
    computed_q = 2**(N-1) + U + 1 - (U % 2)

    # Step 9: Test whether or not q is prime
    if millerRabin(q):
        print("q is not prime")
        return "INVALID"

    n = ceil(L / outlen) - 1

    # Step 4
    b = L - 1 - (n*outlen)

    offset = 1
    for i in range(c):
        V = []
        for j in range(n):
            m = hashlib.sha256()
            m.update(str(dps + offset + j).encode('utf-8'))
            v = int(m.hexdigest(), 16) % (2 ** seedlen)
            V.append(v)
        W = 0
        for a, v in zip(range(n-2), V):
            W += v * 2**(outlen*a)
        W += (V[n-1] % (2**b)) * (2** (n*outlen))
        X = W + 2 ** (L - 1)
        c = X % (2*q)
        computed_p = X - (c - 1)
        prime = False
        if not p < (2 ** (L - 1)):
            if not millerRabin(computed_p):
                print("p = %s is prime afterall" % str(p))
                print("VALID PRIMES")
                prime = True
        if not prime:
            offset += (n + 1)

    if (i != c) or (computed_p != p) or (not millerRabin(computed_p)):
        return "INVALID"

    return "VALID"

if __name__ == '__main__':
    N = 160 # The desired length of the prime p (in bits)
    L = 1024 # The desired length of the prime q (in bits)
    seedlen = N # The desired length of the domain parameter seed; seedlen shall be equal to or greater than N
    outlen = N # outlen is the bit length of the hash function output block
    for _ in range(1000000):
        p, q, dps, c = generate_probable_prime(L, N, seedlen)
        if "VALID" == validate_probable_primes(p, q, dps, c):
            exit()
