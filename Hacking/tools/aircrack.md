# Aircrack

Aircrack is used for password cracking. After capturing all the packets using airodump, we can crack the key by aircrack.
It cracks these keys using two methods PTW and FMS. 
PTW approach is done in two phases. At first, only the ARP packets are being used, and only then, if the key is not cracked after the searching, it uses all the other captured packets. 
A plus point of the PTW approach is that not all the packets are used for the cracking. In the second approach, i.e., FMS, we use both the statistical models and the brute force algos for cracking the key. 
A dictionary method can also be used.
