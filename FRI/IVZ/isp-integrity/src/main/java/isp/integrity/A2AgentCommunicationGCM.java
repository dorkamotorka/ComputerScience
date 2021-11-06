package isp.integrity;

import fri.isp.Agent;
import fri.isp.Environment;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * TASK:
 * Assuming Alice and Bob know a shared secret key, secure the channel using a
 * AES in GCM. Then exchange ten messages between Alice and Bob.
 * <p>
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/crypto/Cipher.html
 */
public class A2AgentCommunicationGCM {
    public static void main(String[] args) throws Exception {
        /*
         * Alice and Bob share a secret session key that will be
         * used for AES in GCM.
         */
        final Key key = KeyGenerator.getInstance("AES").generateKey();
        final Cipher aesgcm = Cipher.getInstance("AES/GCM/NoPadding");
        final Cipher aesgcmd = Cipher.getInstance("AES/GCM/NoPadding");

        final Environment env = new Environment();

        env.add(new Agent("alice") {
            @Override
            public void task() throws Exception {
                final String text = "I hope you get this message intact and in secret. Kisses, Alice.";
                final byte[] pt = text.getBytes(StandardCharsets.UTF_8);
                for (int i = 0 ; i < 10 ; i++) {
                    aesgcm.init(Cipher.ENCRYPT_MODE, key);
                    final byte[] ct = aesgcm.doFinal(pt);
                    send("bob", ct);

                    final byte[] msg = receive("bob");
                    final byte[] iv = aesgcm.getIV();
                    System.out.printf("IV:  %s%n", Agent.hex(iv));
                    aesgcmd.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));
                    final byte[] back = aesgcmd.doFinal(msg);
                    System.out.printf("MSG: %s%n", new String(back, StandardCharsets.UTF_8));
                }
            }
        });

        env.add(new Agent("bob") {
            @Override
            public void task() throws Exception {
                for (int i = 0 ; i < 10 ; i++) {
                    final byte[] ct = receive("alice");
                    final byte[] iv = aesgcm.getIV();
                    System.out.printf("IV:  %s%n", Agent.hex(iv));
                    aesgcmd.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));
                    final byte[] pt = aesgcmd.doFinal(ct);
                    System.out.printf("MSG: %s%n", new String(pt, StandardCharsets.UTF_8));

                    final String msg = "Hey, I Got it";
                    aesgcm.init(Cipher.ENCRYPT_MODE, key);
                    final byte[] out = aesgcm.doFinal(msg.getBytes());
                    send("alice", out);
                }
            }
        });

        env.connect("alice", "bob");
        env.start();
    }
}
