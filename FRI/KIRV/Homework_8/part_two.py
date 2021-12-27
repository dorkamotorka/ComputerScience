#!/usr/bin/env python

import gmpy2

a = 89 # base
p = 1235789 # prime - size of the finite group
B = [2, 3, 5, 7, 11, 13, 17, 19, 23] # factor bas # factor base

def func(cands):
    cands2 = {}
    for c, v in cands.items():
        keys = v[1]
        for b in reversed(B):
            if v[0] % b == 0:
                x = v[0]/b
                keys.append(b)
                cands2[c] = (x, keys)
                break
    return cands2

cands = {}
for i in range(10000):
    #cands=[(354333), gmpy2.mpz(134864), gmpy2.mpz(1087339)]
    y = gmpy2.powmod(gmpy2.mpz(a), gmpy2.mpz(i), gmpy2.mpz(p))
    y = gmpy2.t_mod(354333*y, p)
    keys = []
    for b in reversed(B):
        if y % b == 0:
            x = y/b
            keys.append(b) 
            cands[y] = (x, keys)
            break

#print(cands)
print(len(cands))

cands1 = cands
for i in range(10):
    cands1 = func(cands1)
    for c, v in cands1.items():
        if v[0] == 1:
            #print("SOLUTION!")
            #print(str(c) + " is factorized to " + str(v[1]))
            # Return power
            for i in range(10000):
                y = gmpy2.powmod(gmpy2.mpz(a), gmpy2.mpz(i), gmpy2.mpz(p))
                y = gmpy2.t_mod(354333*y, p)
                if y == c:
                    #print(str(y) + " is calculated from power " + str(i))
                    keys = []
                    for b in reversed(B):
                        if i % b == 0:
                            x = i/b
                            keys.append(b) 
                            cands[i] = (x, keys)
                            break
                    for j in range(10):
                        cands1 = func(cands1)
                        for c, v in cands1.items():
                            if v[0] == 1:
                                print("SOLUTION!")
                                print(str(i) + " is factorized to " + str(v[1]))
    #print(cands)
    #print(len(cands))

