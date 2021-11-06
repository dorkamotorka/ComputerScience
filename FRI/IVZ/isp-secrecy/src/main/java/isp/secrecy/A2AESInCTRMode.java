package isp.secrecy;

import fri.isp.Agent;
import fri.isp.Environment;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * TASK:
 * Assuming Alice and Bob know a shared secret key in advance, secure the channel using a
 * AES in counter mode. Then exchange ten messages between Alice and Bob.
 * <p>
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/crypto/Cipher.html
 */
public class A2AESInCTRMode {
    public static void main(String[] args) throws Exception {
        // STEP 1: Alice and Bob beforehand agree upon a cipher algorithm and a shared secret key
        // This key may be accessed as a global variable by both agents
        final Key key = KeyGenerator.getInstance("AES").generateKey();
        final Cipher encrypt = Cipher.getInstance("AES/CTR/PKCS5Padding");
        final Cipher decrypt = Cipher.getInstance("AES/CTR/PKCS5Padding");

        // STEP 2: Setup communication
        final Environment env = new Environment();

        env.add(new Agent("alice") {
            @Override
            public void task() throws Exception {
                /* TODO STEP 3:
                 * Alice creates, encrypts and sends a message to Bob. Bob replies to the message.
                 * Such exchange repeats 10 times.
                 *
                 * Do not forget: In CBC (and CTR mode), you have to also
                 * send the IV. The IV can be accessed via the
                 * cipher.getIV() call
                 */
                for (int i = 0 ; i < 10 ; i++) {
                    // sends msg
                    final byte[] payload = "I love you Bob. Kisses, Alice.".getBytes();
                    final byte[] iv = new byte[16];
                    new SecureRandom().nextBytes(iv);
                    //print("IV send is '%s'", hex(iv));
                    encrypt.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
                    final byte[] cipherText = encrypt.doFinal(payload);
                    //System.out.println("Send to Bob: " + Agent.hex(cipherText));
                    send("bob", cipherText);

                    // receives feedback
                    final byte[] feedback = receive("bob");
                    final byte[] iv1 = encrypt.getIV();
                    decrypt.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv1));
                    final byte[] dt = decrypt.doFinal(feedback);
                    print("Received from Bob: '%s'", new String(dt));
                }
            }
        });

        env.add(new Agent("bob") {
            @Override
            public void task() throws Exception {
                /* TODO STEP 4
                 * Bob receives, decrypts and displays a message.
                 * Once you obtain the byte[] representation of cipher parameters,
                 * you can load them with:
                 *
                 *   IvParameterSpec ivSpec = new IvParameterSpec(iv);
                 *   aes.init(Cipher.DECRYPT_MODE, my_key, ivSpec);
                 *
                 * You then pass this object to the cipher init() method call.*
                 */
                for (int i = 0 ; i < 10 ; i++) {
                    // sends msg
                    final byte[] payload = "I love you Bob. Kisses, Alice.".getBytes();
                    final byte[] iv = new byte[16];
                    new SecureRandom().nextBytes(iv);
                    //print("IV send is '%s'", hex(iv));
                    encrypt.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
                    final byte[] cipherText = encrypt.doFinal(payload);
                    //System.out.println("Send to Bob: " + Agent.hex(cipherText));
                    send("bob", cipherText);

                    // receives feedback
                    final byte[] feedback = receive("bob");
                    final byte[] iv1 = encrypt.getIV();
                    decrypt.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv1));
                    final byte[] dt = decrypt.doFinal(feedback);
                    print("Received from Bob: '%s'", new String(dt));
                }
            }
        });

        env.connect("alice", "bob");
        env.start();
    }
}
