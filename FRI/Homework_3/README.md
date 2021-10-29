# Homework 3

## Part one

Prove that in KeeLoq, decryption is an inverse operation of encryption.

Neccesary explanation:
- Plaintext is put into a 32-bit register
- Index **i** is number of the round
- Index **j** is the number of the bit in the 32-bit word 
- Key is denoted as **k** and it has 64 bits
- Encryption is done in 528 rounds
- Before round i, bit j being L(i+j), becomes L(i+32) on the spot of 31 bit after round i 

### Encryption steps:

- Initialize with the plaintext: 

	L(31),…,L(0) := P(31),…,P(0)

- for 528(0 ... 527) rounds do:

	L(i+32) := k(i mod 64) ⊕ L(i) ⊕ L(i + 16) ⊕ NLF(L(i+31),L(i+26),L(i+20),L(i+9),L(i+1)) (equation 1)

- Cipher text becomes:
	
	C(31),…,C(0) := L(528+31),…,L(528)

### Decryption steps:

- Initialize with the ciphertext: 
		
	C(31),…,C(0) := L(528+31),…,L(528)

- for 528(528 ... 1) rounds do:

	L(i−1) := k(i−1 mod 64) ⊕ L(i+31) ⊕ L(i+15) ⊕ NLF(L(i+30),L(i+25),L(i+19),L(i+8),L(i+0)) (equation 2)

- Plain text becomes:

	P(31),…,P(0) := L(31),…,L(0)

### Proof Decrypt(Encrypt(x)) = x

We need to prove that equation 1 and 2 are indeed equivalent.
We do that by shifting an index of equation 2 on for +33 and we we acquire equation that becomes:

	L(i+32) := k(i+32 mod 64) ⊕ L(i) ⊕ L(i + 16) ⊕ NLF(L(i+31),L(i+26),L(i+20),L(i+9),L(i+1)) (equation 1)
