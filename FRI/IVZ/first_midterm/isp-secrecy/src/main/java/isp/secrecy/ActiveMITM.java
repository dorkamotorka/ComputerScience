package isp.secrecy;

import fri.isp.Agent;
import fri.isp.Environment;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class ActiveMITM {
    public static void main(String[] args) throws Exception {
        // David and FMTP server both know the same shared secret key
        final Key key = KeyGenerator.getInstance("AES").generateKey();

        final Environment env = new Environment();

        env.add(new Agent("david") {
            @Override
            public void task() throws Exception {
                final String message = "prf.denis@fri.si\n" +
                        "david@fri.si\n" +
                        "Some ideas for the exam\n\n" +
                        "Hi! Find attached <some secret stuff>!";

                final Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
                aes.init(Cipher.ENCRYPT_MODE, key);
                final byte[] ct = aes.doFinal(message.getBytes(StandardCharsets.UTF_8));
                final byte[] iv = aes.getIV();
                print("sending: '%s' (%s)", message, hex(ct));
                send("server", ct);
                send("server", iv);
            }
        });

        env.add(new Agent("student") {
            @Override
            public void task() throws Exception {
                byte[] bytes = receive("david");
                byte[] iv = receive("david");
                print(" IN: %s", hex(bytes));
                print(" IV: %s", hex(iv));

                // As the person-in-the-middle, modify the ciphertext
                // so that the FMTP server will send the email to you
                final byte p = 112;
                final byte r = 114;
                final byte f = 102;
                final byte pikap = 46;
                final byte d = 100;
                final byte e = 101;
                final byte n = 110;
                final byte iii = 105;
                final byte s = 115;
                final byte afnaa = 64;
                final byte ff = 102;
                final byte rr = 114;
                final byte iiii = 105;
                final byte pikapp = 46;
                final byte ss = 115;
                final byte iiiii = 105;
                final byte fir = (byte) (iv[0] ^ p);
                final byte sec = (byte) (iv[1] ^ r);
                final byte thr = (byte) (iv[2] ^ f);
                final byte four = (byte) (iv[3] ^ pikap);
                final byte fift = (byte) (iv[4] ^ d);
                final byte six = (byte) (iv[5] ^ e);
                final byte sev = (byte) (iv[6] ^ n);
                final byte eig = (byte) (iv[7] ^ iii);
                final byte nin = (byte) (iv[8] ^ s);
                final byte ten = (byte) (iv[9] ^ afnaa);
                final byte ele = (byte) (iv[10] ^ ff);
                final byte twe = (byte) (iv[11] ^ rr);
                final byte thir = (byte) (iv[12] ^ iiii);
                final byte fourt = (byte) (iv[13] ^ pikapp);
                final byte fifth = (byte) (iv[14] ^ ss);
                final byte sixth = (byte) (iv[15] ^ iiiii);

                // cipher decryption xor new_iw = my_gmail
                final byte i = 105;
                final byte sss = 115;
                final byte pp = 112;
                final byte dvojka = 50;
                final byte enka = 49;
                final byte afna = 64;
                final byte g = 103;
                final byte m = 109;
                final byte a = 97;
                final byte ii = 105;
                final byte l = 108;
                final byte pika = 46;
                final byte c = 99;
                final byte o = 111;
                final byte mm = 109;
                final byte apo = 9;
                final byte firx = (byte) (i ^ fir);
                final byte secx = (byte) (sss ^ sec);
                final byte thrx = (byte) (pp ^ thr);
                final byte fourx = (byte) (dvojka ^ four);
                final byte fiftx = (byte) (enka ^ fift);
                final byte sixx = (byte) (afna ^ six);
                final byte sevx = (byte) (g ^ sev);
                final byte eigx = (byte) (m ^ eig);
                final byte ninx = (byte) (a ^ nin);
                final byte tenx = (byte) (ii ^ ten);
                final byte elex = (byte) (l ^ ele);
                final byte twex = (byte) (pika ^ twe);
                final byte thirx = (byte) (c ^ thir);
                final byte fourtx = (byte) (o ^ fourt);
                final byte fifthx = (byte) (mm ^ fifth);
                final byte sixthx = (byte) (apo ^ sixth);
                iv[0] = firx;
                iv[1] = secx;
                iv[2] = thrx;
                iv[3] = fourx;
                iv[4] = fiftx;
                iv[5] = sixx;
                iv[6] = sevx;
                iv[7] = eigx;
                iv[8] = ninx;
                iv[9] = tenx;
                iv[10] = elex;
                iv[11] = twex;
                iv[12] = thirx;
                iv[13] = fourtx;
                iv[14] = fifthx;
                iv[15] = sixthx;

                print("OUT: %s", hex(bytes));
                send("server", bytes);
                send("server", iv);
            }
        });

        env.add(new Agent("server") {
            @Override
            public void task() throws Exception {
                final byte[] ct = receive("david");
                final byte[] iv = receive("david");
                final Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
                aes.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
                final byte[] pt = aes.doFinal(ct);
                final String message = new String(pt, StandardCharsets.UTF_8);

                print("got: '%s' (%s)", message, hex(ct));
            }
        });

        env.mitm("david", "server", "student");
        env.start();
    }
}
