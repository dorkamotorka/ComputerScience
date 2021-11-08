# Function notes

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


## Decryption Cipher initialization

- RC4

	final Cipher decrypt = Cipher.getInstance("RC4");
        decrypt.init(Cipher.DECRYPT_MODE, key);

- ChaCha20

	final Cipher decrypt = Cipher.getInstance("ChaCha20");
	byte[] nonce1 = new byte[12];
        new SecureRandom().nextBytes(nonce1);
        decrypt.init(Cipher.DECRYPT_MODE, key, new ChaCha20ParameterSpec(nonce1, i+1));

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
