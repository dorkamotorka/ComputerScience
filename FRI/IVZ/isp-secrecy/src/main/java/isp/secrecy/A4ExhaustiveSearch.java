package isp.secrecy;

import fri.isp.Agent;

import javax.crypto.Cipher;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Implement a brute force key search (exhaustive key search) if you know that the
 * message is:
 * "I would like to keep this text confidential Bob. Kind regards, Alice."
 * <p>
 * Assume the message was encrypted with "DES/ECB/PKCS5Padding".
 * Also assume that the key was poorly chosen. In particular, as an attacker,
 * you are certain that all bytes in the key, with the exception of th last three bytes,
 * have been set to 0.
 * <p>
 * The length of DES key is 8 bytes.
 * <p>
 * To manually specify a key, use the class {@link javax.crypto.spec.SecretKeySpec})
 */
public class A4ExhaustiveSearch {
    public static void main(String[] args) throws Exception {
        final String message = "I would like to keep this text confidential Bob. Kind regards, Alice.";
        System.out.println("[MESSAGE] " + message);
        // TODO
        final Cipher encrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
        //final byte[] key = new byte[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
        //final byte[] key = new byte[] { 0x00, 0x00, 0x01, 0x23 };
        final String val = "0000000000000000000000000000000000000000111111111111111111111111";
        //final String val = "0";
        byte[] key = new BigInteger(val, 8).toByteArray();
        final SecretKeySpec secret = new SecretKeySpec(key, 0, 8, "DES");
        System.out.println("Key: " + Agent.hex(key));
        System.out.println("Sec: " + Agent.hex(secret.getEncoded()));

        encrypt.init(Cipher.ENCRYPT_MODE, secret);
        final byte[] cipherText = encrypt.doFinal(message.getBytes());

        // Brute-force attack password
        bruteForceKey(cipherText, message);
    }

    public static byte[] bruteForceKey(byte[] ct, String message) throws Exception {
        // TODO
        // First 5 bytes are 0's
        // Brute-force last 3 bytes and match message with ct
        final Cipher decrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
        for (int i=0; i<16777216; i++) {
            //System.out.println(Integer.toBinaryString(i));
            final String val = "0000000000000000000000000000000000000000"+ String.format("%" + 16 + "s", Integer.toBinaryString(i)).replaceAll(" ", "0");
            if (val.length() != 64) {
                continue;
            }
            //System.out.println(val);
            byte[] key = new BigInteger(val, 8).toByteArray();
            System.out.println("Key: " + Agent.hex(key));
            final SecretKeySpec secret = new SecretKeySpec(key, 0, 8, "DES");

            decrypt.init(Cipher.DECRYPT_MODE, secret);
            final byte[] plainText = decrypt.doFinal(ct);
            System.out.println("plain hex: " + Agent.hex(plainText));
            System.out.println("message hex: " + Agent.hex(message.getBytes()));

            if (plainText == message.getBytes()) {
                System.out.println("Found key: " + Agent.hex(key));
                break;
            }
        }

        return null;
    }
}
