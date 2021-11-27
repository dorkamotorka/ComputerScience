# Convergent Encryption

The idea is that duplicated files get's encrypted the same, but at the same time assure all other messages get's encrypted differently.

How is that possible?

We use a hash of the message as a encryption key - which is also kindof a nonce, since generally in practice no two messages can produce the same hash.
