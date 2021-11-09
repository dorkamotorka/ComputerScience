# Host Discovery using nmap

We will be using nmap command to discover hosts.
`-sn` tag means to skip port scan, rather just discover remote machines that are up.
- Add `-O` for possible OS detection

## ICMP Ping

Is a type of echo requests between network interfaces. (Not on any port)
Ussualy used not only for host discovery but also to detemine the latency (back-and-forth time to exchange packets).

Command is:

	nmap -sn -PE <target>

or just:

	ping <target>

## TCP SYN Ping

TCP 3-way handshake is a process which is used in a TCP/IP network to make a connection between the server and client.
The connection is full duplex, and both sides synchronize (SYN) and acknowledge (ACK) each other.
The exchange of these four flags is performed in three steps—SYN, SYN-ACK, and ACK.

TCP SYN uses that fact and sends TCP SYN ping(packets) and listens.
If returned packet from remote machines is:
- TCP ACK (Acknowlegment), this means host is up
- RST (reset), this means host either rejected SYN packet or is down

Command is:

	nmap -sn -PS <target>

By default is sends the packet to port 443.

## UDP Discovery

UDP ping scans have the advantage of being capable of detecting systems behind firewalls with strict TCP filtering but that left UDP exposed.

Command is:

	nmap -sn -PU <target>

## SCTP Discovery

The Stream Control Transmission Protocol (SCTP) ensures reliable, in-sequence transport of messages for UDP. 
Similar to TCP SYN, SCTP packets can be used to determine if a host is online by sending SCTP INIT packets and looking for ABORT or INIT ACK responses.

Command is:

	nmap -sn -PY <target>
