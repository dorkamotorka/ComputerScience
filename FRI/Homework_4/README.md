# Homework 4

## Part one

- Let n = pq be a RSA modulus, and d be the encryption exponent. Show that there are at least 99 distinct cleartexts mm that are invariant under encryption, i.e. m^d \equiv m \pmod{n}m
d≡ m(mod n).

- Prove that in RSA cryptosystem decryption is inverse operation of encryption for all plaintexts x \in \Zx∈ Z (including x \in \Z_n \setminus \Z^∗_nx∈ Zn∖Zn∗).

		# Trditve 
		x∈ Z(N) je obrnljiv (obstaja inverz) <==> gcd(x, N) = 1
		abs(Z*(N)) = fi(N) = (p - 1)*(q - 1) = N - p - q + 1
		Eulerjev teorem: za vse x∈ Z*(N): x^(fi(N)) = 1 mod N
		N = p * q
		e * d = 1 mod fi(N)

		# Dokaz:
		RSA(x) = x^e mod N
		RSA(x)^d = x^(ed) = x^(k*fi(N) + 1) = (x^fi(N))^k * x = x (mod N cela vrstica)

## Part two

Let us demonstrate that for a secure RSA modulus n = pq, the difference |p - q| should not be too small.
- Let d be an integer such that q - p = 2d > 0. Prove that n + d^2 is a perfect square.

		Naj bo d najmanjše možno naravno število, da bo veljalo d^2 > n.
		Če velja da je d^2 - n = h^2 (popoln kvadrat) potem, lahko razstavimo n na n = (d + h)*(d - h) = p*q in dobimo faktorja p in q. 
		Če ne dobimo popolnega kvadrata, rekurzivno poskušamo na sledeč način:
		(d+1)^2 - n -> ali je to popolni kvadrat?
		(d+2)^2 - n -> ali je to popolni kvadrat?
		...
		Eventuelno dobimo kvadrat, saj velja ((x + 1)/2)^2 - x = ((x-1)/2)^2

		Ta algoritem imenujemo Fermatov Faktorizacijski algoritem (Fermat’s factorization algorithm)

- You have just found that n + d^2 is a perfect square for some small integer d. Can you factorize nn efficiently in this case?

		Ja, za majhno razliko med faktorji oz. majhen d je Fermatov Faktorizacijski algoritem precej efektiven.

## Part three
