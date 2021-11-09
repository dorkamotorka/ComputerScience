# Netmask

Network mask is used to create smaller sub networks (subnets) from a larger one.
For example, if the subnet mask is:

	11111111.11111111.11111111.00000000

This means:
- First 3 bytes correspond to the network address
- The last byte is used by subnet hosts

This is particularly useful while scanning the local network for connected clients.
You do that by typing e.g.:
	
	nmap -sn 192.168.50.0/24

Where 24 is the number of bits in the mask (like in the mask above).
