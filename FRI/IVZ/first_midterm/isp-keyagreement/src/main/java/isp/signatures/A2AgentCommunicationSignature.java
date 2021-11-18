package isp.signatures;

import fri.isp.Agent;
import fri.isp.Environment;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;

/*
 * Assuming Alice and Bob know each other's public key, provide integrity and non-repudiation
 * to exchanged messages with ECDSA. Then exchange ten signed messages between Alice and Bob.
 */
public class A2AgentCommunicationSignature {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        final Environment env = new Environment();
        final Signature signer = Signature.getInstance("SHA256withECDSA");
        final KeyPair key = KeyPairGenerator.getInstance("EC").generateKeyPair();
        final Key keyh = KeyGenerator.getInstance("HmacSHA256").generateKey();
        final Mac hash = Mac.getInstance("HmacSHA256");
        final Mac dehash = Mac.getInstance("HmacSHA256");


        // Create key pairs

        env.add(new Agent("alice") {
            @Override
            public void task() throws Exception {
                // create a message, sign it,
                // and send the message, signature pair to bob
                for (int i = 0 ; i < 10 ; i++) {
                    final String msg = "Hey Bob.";
                    signer.initSign(key.getPrivate());
                    signer.update(msg.getBytes(StandardCharsets.UTF_8));
                    final byte[] signature = signer.sign();
                    hash.init(keyh);
                    final byte[] tag = hash.doFinal(msg.getBytes(StandardCharsets.UTF_8));
                    //System.out.println("Signature: " + Agent.hex(signature));
                    send("bob", msg.getBytes(StandardCharsets.UTF_8));
                    send("bob", tag);
                    send("bob", signature);
                    // receive the message signarure pair, verify the signature
                    // repeat 10 times
                }
            }
        });

        env.add(new Agent("bob") {
            @Override
            public void task() throws Exception {
                for (int i = 0; i < 10; i++) {
                    final byte[] msg = receive("alice");
                    final byte[] tag = receive("alice");
                    final byte[] sign = receive("alice");
                    final Signature verifier = Signature.getInstance("SHA256withECDSA");
                    verifier.initVerify(key.getPublic());
                    dehash.init(keyh);
                    final byte[] tag1 = dehash.doFinal(msg);
                    if (verify3(tag, tag1, keyh)) {
                        System.out.println("Tag matches!");
                    } else {
                        System.out.println("Tag does not match");
                    }

                    // Check whether the signature is valid
                    verifier.update(msg);

                    if (verifier.verify(sign)) {
                        System.out.println("Valid signature.");
                        System.out.println(new String(msg));
                    }
                    else {
                        System.err.println("Invalid signature.");
                    }
                }
            }
        });

        env.connect("alice", "bob");
        env.start();
    }
    public static boolean verify3(byte[] tag1, byte[] tag2, Key key) throws NoSuchAlgorithmException, InvalidKeyException {
        final Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);

        final byte[] tagtag1 = mac.doFinal(tag1);
        final byte[] tagtag2 = mac.doFinal(tag2);

        return Arrays.equals(tagtag1, tagtag2);
    }
}