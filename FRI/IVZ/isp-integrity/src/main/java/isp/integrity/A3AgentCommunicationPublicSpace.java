package isp.integrity;

import fri.isp.Agent;
import fri.isp.Environment;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * TASK:
 * We want to send a large chunk of data from Alice to Bob while maintaining its integrity and considering
 * the limitations of communication channels -- we have three such channels:
 * - Alice to Bob: an insecure channel, but has high bandwidth and can thus transfer large files
 * - Alice to Public Space: a secure channel, but has low bandwidth and can only transfer small amounts of data
 * - Bob to Public Space: a secure channel, but has low bandwidth and can only transfer small amounts of data
 * <p>
 * The plan is to make use of the public-space technique:
 * - Alice creates the data and computes its digest
 * - Alice sends the data to Bob, and sends the encrypted digest to Public Space
 * - Channel between Alice and Public space is secured with ChaCha20-Poly1305 (Alice and Public space share
 * a ChaCha20 key)
 * - Public space forwards the digest to Bob
 * - The channel between Public Space and Bob is secured but with AES in GCM mode (Bob and Public space share
 * an AES key)
 * - Bob receives the data from Alice and the digest from Public space
 * - Bob computes the digest over the received data and compares it to the received digest
 * <p>
 * Further instructions are given below.
 * <p>
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/crypto/Cipher.html
 */
public class A3AgentCommunicationPublicSpace {
    public static void main(String[] args) throws Exception {
        final Environment env = new Environment();

        // Create a ChaCha20 key that is used by Alice and the public-space
        Key cha_key = KeyGenerator.getInstance("ChaCha20").generateKey();
        // Create an AES key that is used by Bob and the public-space
        Key aes_key = KeyGenerator.getInstance("AES").generateKey();
        final MessageDigest digestAlgorithm = MessageDigest.getInstance("SHA-256");
        Cipher encrypt = Cipher.getInstance("ChaCha20-Poly1305");
        Cipher decrypt = Cipher.getInstance("ChaCha20-Poly1305");
        final Cipher aes_gcm = Cipher.getInstance("AES/GCM/NoPadding");

        env.add(new Agent("alice") {
            @Override
            public void task() throws Exception {
                // a payload of 200 MB
                final byte[] data = new byte[200 * 1024 * 1024];
                new SecureRandom().nextBytes(data);

                // Alice sends the data directly to Bob
                // The channel between Alice and Bob is not secured
                send("bob", data);
                // Alice then computes the digest of the data and sends the digest to public-space
                // The channel between Alice and the public-space is secured with ChaCha20-Poly1305
                // Use the key that you have created above.
                byte[] nonce = new byte[12];
                new SecureRandom().nextBytes(nonce);
                final byte[] hashed = digestAlgorithm.digest(data);
                System.out.println("Hashed calculated from Alice: "+Agent.hex(hashed));
                final byte[] iv = new byte[12];
                new SecureRandom().nextBytes(iv);
                encrypt.init(encrypt.ENCRYPT_MODE, cha_key, new IvParameterSpec(iv));
                final byte[] ct = encrypt.doFinal(hashed);
                send("public-space", ct);
            }
        });

        env.add(new Agent("public-space") {
            @Override
            public void task() throws Exception {
                // Receive the encrypted digest from Alice and decrypt ChaCha20 and
                // the key that you share with Alice
                final byte[] ct = receive("alice");
                final byte[] iv = encrypt.getIV();
                decrypt.init(decrypt.DECRYPT_MODE, cha_key, new IvParameterSpec(iv));
                final byte[] pt = decrypt.doFinal(ct);
                System.out.println("Public-space received from Alice hashed value: "+Agent.hex(pt));
                // Encrypt the digest with AES-GCM and the key that you share with Bob and
                // send the encrypted digest to Bob
                aes_gcm.init(aes_gcm.ENCRYPT_MODE, aes_key);
                final byte[] ct_out = aes_gcm.doFinal(pt);
                send("bob", ct_out);
            }
        });

        env.add(new Agent("bob") {
            @Override
            public void task() throws Exception {
                // Receive the data from Alice and compute the digest over it using SHA-256
                final byte[] data = receive("alice");
                final byte[] hashed = digestAlgorithm.digest(data);
                System.out.println("Hashed calculated from Bob: "+Agent.hex(hashed));
                // Receive the encrypted digest from the public-space, decrypt it using AES-GCM
                // and the key that Bob shares with the public-space
                final byte[] web = receive("public-space");
                final byte[] iv = aes_gcm.getIV();
                final Cipher aes_gcmd = Cipher.getInstance("AES/GCM/NoPadding");
                aes_gcmd.init(aes_gcmd.DECRYPT_MODE, aes_key, new GCMParameterSpec(128, iv));
                final byte[] data_out = aes_gcmd.doFinal(web);
                // Compare the computed digest and the received digest and print the string
                // "data valid" if the verification succeeds, otherwise print "data invalid"
                if (Arrays.equals(hashed, data_out)) {
                    System.out.println("data valid");
                } else {
                    System.out.println("data invalid - rerun the program (Conccurency issue)");
                }
            }
        });

        env.connect("alice", "bob");
        env.connect("alice", "public-space");
        env.connect("public-space", "bob");
        env.start();
    }
}
