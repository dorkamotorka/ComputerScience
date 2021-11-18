# Function notes

API Reference: https://docs.oracle.com/javase/7/docs/api/javax/crypto/package-summary.html

## Key generation

- AES

		final Key key = KeyGenerator.getInstance("AES").generateKey();

- RC4
	
		final Key key = KeyGenerator.getInstance("RC4").generateKey();


## Encryption Cipher initialization

- RC4

		final Cipher encrypt = Cipher.getInstance("RC4");
		encrypt.init(Cipher.ENCRYPT_MODE, key);

- ChaCha20

		final Cipher encrypt = Cipher.getInstance("ChaCha20");
		final byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		byte[] nonce = new byte[12];
		new SecureRandom().nextBytes(nonce);
		encrypt.init(Cipher.ENCRYPT_MODE, key, new ChaCha20ParameterSpec(nonce, i));

- ChaCha20-Poly1305

		final Cipher encrypt = Cipher.getInstance("ChaCha20-Poly1305");
		final Key cha_key = KeyGenerator.getInstance("ChaCha20").generateKey();
		final byte[] iv = new byte[12];
		new SecureRandom().nextBytes(iv);
		encrypt.init(encrypt.ENCRYPT_MODE, cha_key, new IvParameterSpec(iv));

- AES (Counter mode)

		final Cipher encrypt = Cipher.getInstance("AES/CTR/PKCS5Padding");
		final byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		encrypt.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));

- AES (CBC mode)

		final Cipher encrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
		final byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		encrypt.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));

- GCM

		final Cipher encrypt = Cipher.getInstance("AES/GCM/NoPadding");
		encrypt.init(Cipher.ENCRYPT_MODE, key);

- RSA

		final Cipher encryption = Cipher.getInstance("RSA/ECB/OAEPPadding");
		final KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		final KeyPair bobKP = kpg.generateKeyPair();
		encryption.init(Cipher.ENCRYPT_MODE, bobKP.getPublic());

## Decryption Cipher initialization

- RC4

		final Cipher decrypt = Cipher.getInstance("RC4");
		decrypt.init(Cipher.DECRYPT_MODE, key);

- ChaCha20

		final Cipher decrypt = Cipher.getInstance("ChaCha20");
		byte[] nonce1 = new byte[12];
		new SecureRandom().nextBytes(nonce1);
		decrypt.init(Cipher.DECRYPT_MODE, key, new ChaCha20ParameterSpec(nonce1, i+1));

- ChaCha20-Poly1305
	
		final byte[] iv = encrypt.getIV();
		decrypt.init(decrypt.DECRYPT_MODE, cha_key, new IvParameterSpec(iv));

- AES (Counter mode)

		final Cipher decrypt = Cipher.getInstance("AES/CTR/PKCS5Padding");
		final byte[] iv1 = encrypt.getIV();
		decrypt.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv1));

- AES (CBC mode)

		final Cipher decrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
		final byte[] iv = encrypt.getIV();
		decrypt.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

- GCM

		final byte[] iv = encrypt.getIV();
		final Cipher decrypt = Cipher.getInstance("AES/GCM/NoPadding");
		encrypt.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));

- RSA 
	
		final Cipher decryption = Cipher.getInstance();
		decryption.init(Cipher.DECRYPT_MODE, bobKP.getPrivate("RSA/ECB/OAEPPadding"));

## Message digest (Msg -> Hash)

- SHA-256

		final MessageDigest digestAlgorithm = MessageDigest.getInstance("SHA-256");
		final byte[] hashed = digestAlgorithm.digest(message.getBytes(StandardCharsets.UTF_8));

## Message Authentication Code (MAC)

- HMAC
	
		// (signing)
		final Mac hmac = Mac.getInstance("HmacSHA256");
		final Key key = KeyGenerator.getInstance("HmacSHA256").generateKey();
		hmac.init(key);
		final byte[] tag1 = hmac.doFinal(message.getBytes(StandardCharsets.UTF_8));

		// (verification)
		final Mac hmac2 = Mac.getInstance("HmacSHA256");
		hmac2.init(key);
		final byte[] tag2 = hmac2.doFinal(message.getBytes(StandardCharsets.UTF_8));

- ECDSA

		// (signing)
		final Signature signer = Signature.getInstance("SHA256withECDSA");
		final KeyPair key = KeyPairGenerator.getInstance("EC").generateKeyPair();
		signer.initSign(key.getPrivate());
		signer.update(msg.getBytes(StandardCharsets.UTF_8));
		final byte[] signature = signer.sign();

		// (verification)
		final Signature verifier = Signature.getInstance("SHA256withECDSA");
		verifier.initVerify(key.getPublic());
		verifier.update(msg);
		if (verifier.verify(sign)) { System.out.println("Valid signature."); };
