# Homework 2

## Part one

- The algorithm halts after a finite number of steps (Termination proof)

	Razlog za to je namreč zato, ker z vsakim korakom argumenta funkcije gcd(a,b) postajata manjša oz. njuna vsota je vedno manjša.
	Argumenta se manjšata, dokler eden od njiju ne postane 0 in takrat se algoritem ustavi, medtem ko zgornja meja vsote je a(začetni)+b(začetni).
	Ker se vsaj eden izmed argumentov ob vsakem koraku zmanjša je neizogibnu, da eden izmed njiju postane 0 oz. da se postopek zaključi.

- gcd(a,b)=gcd(r0,r1)=gcd(r1,r2)=...=gcd(rn−1,rn)=rn

Če imamo dva pozitivna integerja a in b, tako da b ne-deli a, vemo da obstajata unikatna integerja q1 in r1, tako, da velja:

	a = b * q1 + r1; 0 < r1 < b

Iz te enačbe sledi, da a in b imata isti skupni delitelj kot integerja b in r1:

	gcd(a, b) = gcd(b, r1)
	
Iz zgornje enačbe sledi oz. najdemo integerja q2 in r2 tako da velja:
	
	b = r1 * q2 + r2; 0 < r2 < r1 (r1 ne-deli b)

Tako lahko nadaljujemo do zadnjih dveh členov (Proces se mora zaključiti, kot smo dokazali v zgornji nalogi):

	r(n-2) = r(n-1) * g(n) + r(n)
	r(n-1) = r(n) * q(n+1)
	
Iz tega je očitno, da:

	gcd(a,b) = gcd(b,r1) = gcd(r1,r2) = ... = gcd(r(n-1), r(n)) = gcd(r(n), 0) = r(n)

## Part two

Code is using gcm function from math library, rather my own implementation.

![image](https://user-images.githubusercontent.com/48418580/138584121-ae40592b-9879-458d-ae3d-9672d9f49f96.png)

And the output graph is:

![Homework2](https://user-images.githubusercontent.com/48418580/138360703-3d42bc30-60fe-4d4f-98b6-ec6a0a87df11.png)
