# Homework 7

## Part one

Since $n = p*q$, where p and q are prime, then for any number y, we have exactly four options:

	gcd(y, n) = 1 (option 1)
	gcd(y, n) = p (option 2)
	gcd(y, n) = q (option 3)
	gcd(y, n) = N (option 4)

We can exclude option 4 because y was reduced modulo n before(RSA), which ensures that y < n. 
Furthermore we assume the case m = 0 happens with negligible probability, since the only encrption that can yield it is one of multiple of modulo n(including 0).
If (gcd(y, n) > 1 and gcd(y, n) != n) we can easily see it is possible to factorize n.

Therefore, suppose that gcd(y, n) = 1.
Now the algorithm **D** should choose a random $a_1 \in \Z_n$ different from 0 such that:

	$x = \frac {x'} {x_1} mod n$

where x' is a decryption of y' returned from **A**, calculated as:

	$y_1 = x_1^{b} mod n$
	$y' = y y_1 mod n$

From this we can conclude:
- If gcd(y, n) > 1, then **D** always succeeds, since n can be easily factorized.

- If gcd(y, n) = 1, then y' is a random non zero element of $\Z_n$, so the success probability is number of non-zero ciphertexts that **A** can successfuly decrypt divided by all non-zero ciphertexts:
	
	\frac{\epsilon(n-1)}{n-1} = {\epsilon}

Therefore for any input y, the success probabilty of **B** is greater than \epsilon.

## Part two

- The probability of n-1 failures followed by a success is:

	$\epsilon^{n-1}$ 

because success and failure are totally independent events, therefore the following holds:

	$p(\failure \cup (success)) = p(failure) * p(success)$

so the probability of success occuring after two failures would be a product of:

	$p(failure) * p(failure) * p(success)$

expressed in mathematical terms:

	$\epsilon * \epsilon * (\epsilon - 1)$

- The average number of trials to achieve success is:

	$\sum^{\infty}_{n=1}(n * p_n)$

following from the result in the first part, we can write:

	$\sum^{\infty}_{n=1}n \epsilon^{n-1}(1 - \epsilon)$

	$(1 - \epsilon) \sum^{\infty}_{n=1} \sum^{n}_{j=1} \epsilon^{n-1}$

      	$(1 - \epsilon) \sum^{\infty}_{j=1} \sum^{\infty}_{n=j} \epsilon^{n-1}$


      	$(1 - \epsilon) \sum^{\infty}_{j=1} \frac{\epsilon^{j-1}}{(1 - \epsilon)}$

      	$\sum^{\infty}_{j=1} \epsilon^{j-1}$

Which can be written as geometric series formula:

      	$\sum^{\infty}_{j=0} \epsilon^{j} = \frac {1}{1-\epsilon}$


- The number of iterations required in order to reduce the probability of failure to at most \delta is:

	$\lceil \frac{log_2 \delta}{log_2 \epsilon} \rceil$	

Since the probability of failure after m trials is $\epsilon^{m}$ and we want it to be \epsilon^{m} \leq \delta, which is equivalent to the following expression:

	$m log_2 \epsilon \leq log_2 \delta$

and since \epsilon is smaller than 1, that means $log_2 \epsilon < 0$ and because $m$ is an integer the following holds:

	$m \geq \lceil \frac{log_2 \delta}{log_2 \epsilon} \rceil$
