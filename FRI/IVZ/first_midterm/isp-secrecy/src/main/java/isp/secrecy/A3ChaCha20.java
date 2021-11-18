package isp.secrecy;

import fri.isp.Agent;
import fri.isp.Environment;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * TASK:
 * Assuming Alice and Bob know a shared secret key in advance, secure the channel using
 * ChaCha20 stream cipher. Then exchange ten messages between Alice and Bob.
 * <p>
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/crypto/Cipher.html
 */
public class A3ChaCha20 {
    public static void main(String[] args) throws Exception {
        // STEP 1: Alice and Bob beforehand agree upon a cipher algorithm and a shared secret key
        // This key may be accessed as a global variable by both agents
        final Key key = KeyGenerator.getInstance("AES").generateKey();
        final Cipher encrypt = Cipher.getInstance("ChaCha20");
        final Cipher decrypt = Cipher.getInstance("ChaCha20");

        // STEP 2: Setup communication
        final Environment env = new Environment();

        env.add(new Agent("alice") {
            @Override
            public void task() throws Exception {
                /* TODO STEP 3:
                 * Alice creates, encrypts and sends a message to Bob. Bob replies to the message.
                 * Such exchange repeats 10 times.
                 *
                 * Recall, ChaCha2 requires that you specify the nonce and the counter explicitly.
                 */
                for (int i = 0 ; i < 10 ; i++) {
                    // sends msg
                    final byte[] payload = "I love you Bob. Kisses, Alice.".getBytes();
                    final byte[] iv = new byte[16];
                    new SecureRandom().nextBytes(iv);
                    //print("IV send is '%s'", hex(iv));
                    byte[] nonce = new byte[12];
                    new SecureRandom().nextBytes(nonce);
                    encrypt.init(Cipher.ENCRYPT_MODE, key, new ChaCha20ParameterSpec(nonce, i));
                    final byte[] cipherText = encrypt.doFinal(payload);
                    //System.out.println("Send to Bob: " + Agent.hex(cipherText));
                    send("bob", cipherText);

                    // receives feedback
                    final byte[] feedback = receive("bob");
                    byte[] nonce1 = new byte[12];
                    new SecureRandom().nextBytes(nonce1);
                    decrypt.init(Cipher.DECRYPT_MODE, key, new ChaCha20ParameterSpec(nonce1, i+1));
                    final byte[] dt = decrypt.doFinal(feedback);
                    print("Received from Bob: '%s'", new String(dt));
                }
            }
        });

        env.add(new Agent("bob") {
            @Override
            public void task() throws Exception {
                // TODO
                for (int i = 0 ; i < 10 ; i++) {
                    // sends msg
                    final byte[] payload = "I love you Bob. Kisses, Alice.".getBytes();
                    byte[] nonce = new byte[12];
                    new SecureRandom().nextBytes(nonce);
                    encrypt.init(Cipher.ENCRYPT_MODE, key, new ChaCha20ParameterSpec(nonce, i));
                    final byte[] cipherText = encrypt.doFinal(payload);
                    //System.out.println("Send to Bob: " + Agent.hex(cipherText));
                    send("bob", cipherText);

                    // receives feedback
                    final byte[] feedback = receive("bob");
                    byte[] nonce1 = new byte[12];
                    new SecureRandom().nextBytes(nonce1);
                    decrypt.init(Cipher.DECRYPT_MODE, key, new ChaCha20ParameterSpec(nonce1, i+1));
                    final byte[] dt = decrypt.doFinal(feedback);
                    print("Received from Bob: '%s'", new String(dt));
                }
            }
        });

        env.connect("alice", "bob");
        env.start();
    }
}
