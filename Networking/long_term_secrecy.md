# (Perfect) Forward Secrecy

If an algorithm/system possesses PFS, that means for each session/request-response a new shared secret is established (e.g. using DH).
Consequently all past session data are safe, if a shared secret of current session is compromised.

# Backward Secrecy

Is the other way around, so if the attacker knows past secret key, he is not able to compromise future secret key.
