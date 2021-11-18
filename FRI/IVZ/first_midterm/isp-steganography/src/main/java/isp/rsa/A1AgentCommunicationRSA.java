package isp.rsa;

import fri.isp.Agent;
import fri.isp.Environment;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Assuming Alice and Bob know each other's public key, secure the channel using a
 * RSA. Then exchange ten messages between Alice and Bob.
 *
 * (The remaining assignment(s) can be found in the isp.steganography.ImageSteganography
 * class.)
 */
public class A1AgentCommunicationRSA {
    public static void main(String[] args) throws Exception {

        final String algorithm = "RSA/ECB/OAEPPadding";
        // Create two public-secret key pairs
        final KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        final KeyPair KP = kpg.generateKeyPair();
        final KeyPairGenerator kpg1 = KeyPairGenerator.getInstance("RSA");
        final KeyPair KP1 = kpg.generateKeyPair();

        final Environment env = new Environment();

        env.add(new Agent("alice") {
            @Override
            public void task() throws Exception {
                final String msg1 = "What's up Bob";
                for (int i = 0 ; i < 10 ; i++) {
                    final Cipher rsaEnc = Cipher.getInstance(algorithm);
                    rsaEnc.init(Cipher.ENCRYPT_MODE, KP.getPublic());
                    final byte[] ct = rsaEnc.doFinal(msg1.getBytes(StandardCharsets.UTF_8));
                    //System.out.println("Message: " + msg1);
                    send("bob", ct);

                    final byte[] msg2 = receive("bob");
                    final Cipher rsaDec = Cipher.getInstance(algorithm);
                    rsaDec.init(Cipher.DECRYPT_MODE, KP1.getPrivate());
                    final byte[] decryptedText = rsaDec.doFinal(msg2);
                    System.out.println("Message: " + new String(decryptedText, StandardCharsets.UTF_8));
                }
            }
        });

        env.add(new Agent("bob") {
            @Override
            public void task() throws Exception {
                final String msg2 = "Hey Alice";
                for (int i = 0 ; i < 10 ; i++) {
                    final byte[] msg1 = receive("alice");
                    final Cipher rsaDec = Cipher.getInstance(algorithm);
                    rsaDec.init(Cipher.DECRYPT_MODE, KP.getPrivate());
                    final byte[] decryptedText = rsaDec.doFinal(msg1);
                    System.out.println("Message: " + new String(decryptedText, StandardCharsets.UTF_8));

                    final Cipher rsaEnc = Cipher.getInstance(algorithm);
                    rsaEnc.init(Cipher.ENCRYPT_MODE, KP1.getPublic());
                    final byte[] ct = rsaEnc.doFinal(msg2.getBytes(StandardCharsets.UTF_8));
                    //System.out.println("Message: " + msg2);
                    send("alice", ct);
                }
            }
        });

        env.connect("alice", "bob");
        env.start();
    }
}
