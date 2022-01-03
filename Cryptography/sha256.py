#!/usr/bin/python

from hashlib import sha256
import json

def compute_hash(obj):
    block_string = json.dumps(obj)
    return sha256(block_string.encode()).hexdigest()

if __name__ == '__main__':
    d1 = {
        1 : "first",
        2 : "second",
        3 : "third",
    }
    hashed1 = compute_hash(json.dumps(d1))
    print(hashed1)

    d2 = {
        1 : "first",
        2 : "second",
        3 : "third",
    }
    hashed2 = compute_hash(json.dumps(d2))
    print(hashed2)

    d3 = {
        1 : "first",
        2 : "second",
        3 : "third",
        4 : "fourth",
    }
    hashed3 = compute_hash(json.dumps(d3))
    print(hashed3)

print(hashed1 == hashed2)
print(hashed3 == hashed2)
