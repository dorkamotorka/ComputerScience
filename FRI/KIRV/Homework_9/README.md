# Homework 8

## Part one

In order to verify $(\lambda, \mu)$ is a valid ElGamal signature of $x'$ we need to show that:
	
   $\beta^{\lambda} \lambda^{\mu} \equiv \alpha^{x'} (modp)$

We proceed as follows:

   $$
   \alpha^{x^{\prime}} \equiv \beta^{\lambda} \lambda^{\mu}(\bmod p) \\

   \alpha^{x^{\prime}} \equiv \beta^{\lambda}\left(\alpha^{i} \beta^{j} \gamma^{h}\right)^{\mu}(\bmod p) \\

   \alpha^{x^{\prime}} \equiv \beta^{\lambda+j \mu} \alpha^{i \mu} \gamma^{h \mu}(\bmod p) \\

   \alpha^{x^{\prime}-i \mu} \equiv \beta^{\lambda+j \mu} \gamma^{h \mu}(\bmod p) \\

   \alpha^{\left(x^{\prime}-i \mu\right) \gamma} \equiv \beta^{(\lambda+j \mu) \gamma} \gamma^{h \mu \gamma}(\bmod p) \\

Exponentiate with $\mu$ on both sides:

   \alpha^{\left(x^{\prime}-i \mu\right) \gamma} \equiv \beta^{(\lambda+j \mu) \gamma} \gamma^{(\lambda+j \mu) \delta}(\bmod p) \\

   \alpha^{\left(x^{\prime}-i \mu\right) \gamma} \equiv\left(\beta^{\gamma} \gamma^{\delta}\right)^{(\lambda+j \mu)}(\bmod p) \\

   \alpha^{\left(x^{\prime}-i \mu\right) \gamma} \equiv\left(\alpha^{x}\right)^{(\lambda+j \mu)}(\bmod p) \\

Take logarithm of $\alpha$ on both sides:

   \left(x^{\prime}-i \mu\right) \gamma \equiv x(\lambda+j \mu)(\bmod p-1) \\

   x^{\prime}\gamma - i \mu \gamma \equiv x \lambda + x j \mu(\bmod p-1) \\

   x^{\prime} \gamma-x \lambda \equiv \mu(x j+i \gamma)(\bmod p-1) \\

Insert $\mu$ on the right side and multiply with the denominator:

   (h \gamma-j \delta)\left(x^{\prime} \gamma-x \lambda\right) \equiv \lambda \delta(x j+i \gamma)(\bmod p-1) \\

   x^{\prime} \gamma(h \gamma-j \delta) \equiv x \lambda(h \gamma-j \delta)+\lambda \delta(x j+i \gamma)(\bmod p-1) \\

Divide by (h \gamma-j \delta) on both sides:

   x^{\prime} \gamma \equiv x \lambda +\mu(x j+i \gamma)(\bmod p-1) \\

   x^{\prime} \gamma \equiv \gamma \lambda(i \delta+x h)(\bmod p-1) \\

We get $x^{\prime}$:

   x^{\prime} \equiv \lambda(i \delta+x h)(h \gamma-j \delta)^{-1}(\bmod p-1)
   $$

## Part two

Generation and validation of p and q prime numbers done in appended script(part_two.py), with documented steps.
