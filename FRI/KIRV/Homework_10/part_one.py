#!/usr/bin/env python

import hashlib
import string
import random
from binascii import hexlify, unhexlify
from math import sqrt

# Return random string
def get_random_string(slen):
    return (''.join(random.choice(string.ascii_lowercase) for i in range(slen))).encode() 

if __name__ == '__main__':
    i = 0
    for slen in range(1, 10):
        lookup = {}
        max_tries = int(sqrt(26**(slen))) # Birthday paradox

        for _ in range(26**(slen)):
            rstr = get_random_string(slen)
            #print(rstr)
            random_hex = hexlify(rstr)
            truncated_hash = hashlib.sha256(random_hex).hexdigest()[-10:]
            # If same hash was already observed before
            if truncated_hash in lookup:
                word = lookup[truncated_hash]
                u = unhexlify(random_hex.decode())
                if unhexlify(word) != u:
                    i += 1
                    print("Collision #%s found" % i)
                    print("New word with same (truncated) hash:                 %s, hex is %s, hash is %s" % (u.decode(), random_hex.decode(), truncated_hash))
                    print("Previously stored word with same (truncated) hash:   %s, hex is %s, hash is %s" % (unhexlify(word).decode(), word.decode(), truncated_hash))
                    print("Size of stored (word, hash) pairs for %s-character word until collision found: %s" % (slen, len(lookup)))
                    break
            else:
                lookup[truncated_hash] = random_hex
