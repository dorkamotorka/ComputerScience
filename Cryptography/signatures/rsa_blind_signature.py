#!/usr/bin/env python

# GOAL: We want that somebody signs a document and at the same time we do not want this person to see its content. (e.g. notary, a bank official when dealing with digital money)
m = 89 # In practice string is converted integer

# Small numbers are chosen - in practice these numbers are large
p = 3
q = 11
fi = (p - 1) * (q - 1)

# RSA Bob public key
n = p * q
e = 7 # must be comprime to fi

# RSA Bob private key
d = 3 # d*e mod fi = 1 must hold

# Step 1: Alice chooses a secret non-negative integer k smaller than (n-1) that is coprime to n 
k = 8
k_inverse = 29 # k*k_inverse mod n = 1 must hold

# Step 2: Masks/blinds the message using Bobs public key and sends it to Bob
blind_msg = m * (k ** e) % n

# Step 3: Bob signs the masked message and sends it back to Alice
signed_blind_msg = (blind_msg ** d) % n

# Step 4: Alice unmasks the signed masked message 
signed_msg = (k_inverse * signed_blind_msg) % n

# Verification of signature
assert signed_msg == (m ** d) % n
