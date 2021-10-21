# Homework 2

## Part one

- The algorithm halts after a finite number of steps (Termination proof)

	Razlog za to je namreč zato, ker z vsakim korakom argumenta funkcije gcd(a,b) postajata manjša oz. njuna vsota je vedno manjša.
	Argumenta se manjšata, dokler eden od njiju ne postane 0 in takrat se algoritem ustavi, medtem ko zgornja meja vsote je a(začetni)+b(začetni).
	Ker se vsaj eden izmed argumentov ob vsakem koraku zmanjša je neizogibnu, da eden izmed njiju postane 0 oz. da se postopek zaključi.

- gcd(a,b)=gcd(r0,r1)=gcd(r1,r2)=...=gcd(rn−1,rn)=rn

## Part two

Code is using gcm function from math library, rather my own implementation.

![Homework2](https://user-images.githubusercontent.com/48418580/138360703-3d42bc30-60fe-4d4f-98b6-ec6a0a87df11.png)
