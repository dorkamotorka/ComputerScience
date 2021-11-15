# Homework 4

## Part one

- Let n = pq be a RSA modulus, and d be the encryption exponent. Show that there are at least 9 distinct cleartexts mm that are invariant under encryption, i.e. m^d \equiv m \pmod{n}m
d≡ m(mod n).

		Ker sta p in q različni lihi praštevili in d lihi, sledi iz:

			m^d = m (mod p)
			m^d = m (mod q)
	
		da kot posledica Chinese Remainder Theorema(CRT) velja tudi:

			m^d = m (mod n)

		DOKAZ: Rešitev sistema

			m^d = m (mod p)
			m^d = m (mod q)
		
		je po postopku CRT enaka

			m^d = mq*q^-1 + mp*p^-1 (mod pq)
			m^d = m (q*q^-1 + p*p^-1) (mod n)
		
		Pri čemer je izraz v oklepaju enak 1, saj velja

			gcd(p, q) = 1 oz. p*a + q*b = 1

		in zato tudi velja:

			m^d = m (mod n)

		Za vsako izmed teh enačb imamo tri rešitve {0, 1, -1}:
		- 0 in 1 sta trivialni rešitvi enačbe, saj:

			0^d = 0 (mod n,p,q)
			1^d = 1 (mod n,p,q) 

		- -1 pa je rešitev, ker je d lih, saj:

			(-1)^d = -1 (mod n,p,q)

		katerikoli lih koeficient bo d bo enačba veljala.
		
		Torej našli smo 9 tekstov neodvisnih od enkripcije, 3 za vsako enačbo.

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
