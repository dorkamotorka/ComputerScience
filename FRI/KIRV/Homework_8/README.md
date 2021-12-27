# Homework 8

## Part one

## Part two

Using appended script we find the following factorization of numbers:

	$89^{1988} \equiv 531441 \equiv 3^{12}$
	$89^1072 \equiv 425425 \equiv 5^{2}*7*11*13*17$
	$89^1126 \equiv 788785 \equiv 5*19^{3}*23$
	$89^1179 \equiv 459680 \equiv 2^{5}*5*13^{2}*17$
	$89^1230 \equiv 23667 \equiv 3*7^{3}*23$
	$89^1237 \equiv 14 \equiv 2*7$
	$89^1246 \equiv 33327 \equiv 3^{2}*7*23^{2}$
	$89^1462 \equiv 10450 \equiv 2*5^{2}*11*19$
	$89^1606 \equiv 239360 \equiv 2^{8}*5*11*17$
	$89^1711 \equiv 453789 \equiv (-1)^{2}*3^{3}*7^{5}$ # Tukaj sem dodal (-1)

We take a $log_89$ of both sides and get a system of equations, 

	1988 = 12*c
	1072 = 2*d+e+f+g+h
	1126 = d+3*i+j
	1179 = 5*b+d+2*g+h
	1230 = c+3*e+j
	1237 = b+e
	1246 = 2*c+e+2*j
	1462 = b+2*d+f+i
	1606 = 8*b+d+f+h
	1711 = 2*a+3*c+5*e

where symbols are:

	$a = log_89 -1$
   	$b = log_89 2$
   	$c = log_89 3$
   	$d = log_89 5$
   	$e = log_89 7$
   	$f = log_89 11$
   	$g = log_89 13$
   	$h = log_89 17$
   	$i = log_89 19$
   	$j = log_89 23$

We write that as a matrix and solve matrix equation $mod p$ using Gauss elimination:

	$a = 0$
   	$b = 248152$
   	$c = 824025$
   	$d = 858640$
   	$e = 988874$
   	$f = 766096$
   	$g = 137168$
   	$h = 99021$
   	$i = 977301$
   	$j = 1153739$

We use that to solve for the following exercises:

a) $log_89 354333
	
Solution will be described here, while similar concept applies to exercise b) and c).
We choose search for such exponent $e$, such that we will be able to factorize the product and the exponent using factors from set B:

	\beta*\alpha^{e} \equiv factor-from-set-B

The same as before we modify a bit the appended script and get:
- factorization of product

	$354333*89^{1589} \equiv 117 \equiv 3^{2}*13 mod 1235789$

- factorization of exponent

	$1589 = 2^{8}*3^{3}*5*17$

We take a $log_89$ on both sides and get:

	$log_89 354333 \equiv 2*824025 + 137168 - (8*248152 + 3*824025 + 99021)$
	$log_89 354333 \equiv 936273$
	

b) $log_89 134864
c) $log_89 1087339
