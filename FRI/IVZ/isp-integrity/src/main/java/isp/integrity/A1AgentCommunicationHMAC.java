package isp.integrity;

import fri.isp.Agent;
import fri.isp.Environment;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * TASK:
 * Assuming Alice and Bob know a shared secret key, provide integrity to the channel
 * using HMAC implemted with SHA256. Then exchange ten messages between Alice and Bob.
 * <p>
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/crypto/Cipher.html
 */
public class A1AgentCommunicationHMAC {
    public static void main(String[] args) throws Exception {
        /*
         * Alice and Bob share a secret session key that will be
         * used for hash based message authentication code.
         */
        final Key key = KeyGenerator.getInstance("HmacSHA256").generateKey();
        final Mac hash = Mac.getInstance("HmacSHA256");
        final Mac dehash = Mac.getInstance("HmacSHA256");
        final Environment env = new Environment();

        env.add(new Agent("alice") {
            @Override
            public void task() throws Exception {
                final String text = "I hope you get this message intact. Kisses, Alice.";
                for (int i = 0 ; i < 10 ; i++) {
                    final byte[] pt = text.getBytes(StandardCharsets.UTF_8);
                    hash.init(key);
                    final byte[] tag = hash.doFinal(pt);
                    send("bob", pt);
                    send("bob", tag);
                    System.out.println("Alice's tag: "+Agent.hex(tag));

                    final byte[] msg = receive("bob");
                    final byte[] tag1 = receive("bob");
                    dehash.init(key);
                    final byte[] tag2 = dehash.doFinal(msg);
                    if (verify3(tag1, tag2, key)) {
                        System.out.println("Tag matches!");
                    } else {
                        System.out.println("Tag does not match");
                    }
                }
            }
        });

        env.add(new Agent("bob") {
            @Override
            public void task() throws Exception {
                final String text = "I got it. Kisses, Bob.";
                for (int i = 0 ; i < 10 ; i++) {
                    final byte[] pt = receive("alice");
                    final byte[] tag = receive("alice");
                    dehash.init(key);
                    final byte[] tag1 = dehash.doFinal(pt);
                    System.out.println("Bob's tag: "+Agent.hex(tag1));
                    if (verify3(tag, tag1, key)) {
                        System.out.println("Tag matches!");
                    } else {
                        System.out.println("Tag does not match");
                    }

                    final byte[] msg = text.getBytes(StandardCharsets.UTF_8);
                    hash.init(key);
                    final byte[] tag2 = hash.doFinal(msg);
                    send("alice", msg);
                    send("alice", tag2);
                }
            }
        });

        env.connect("alice", "bob");
        env.start();
    }

    public static boolean verify3(byte[] tag1, byte[] tag2, Key key) throws NoSuchAlgorithmException, InvalidKeyException {
        /*
            FIXME: Defense #2

            The idea is to hide which bytes are actually being compared
            by MAC-ing the tags once more and then comparing those tags
         */
        final Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);

        final byte[] tagtag1 = mac.doFinal(tag1);
        final byte[] tagtag2 = mac.doFinal(tag2);

        return Arrays.equals(tagtag1, tagtag2);
    }
}
