# IPTables

## Terminology 

- **IPTables chains** are a set of rules processed in order (built-in ones are INPUT, OUTPUT, FORWARD)

- **policy** - default behaviour for any non-configured traffic, otherwise the rule is taken into an account

Options:

	- ACCEPT means to let the packet through.
	- DROP means to discard the packet and not send any response
	- REJECT is used to send back an error packet in response to the matched packet
	- QUEUE means to pass the packet to userspace
	- RETURN means stop traversing this chain and resume at the next rule in the previous (calling) chain

## Command options

- list iptables configuration

		sudo iptables -L -v

- remove/delete all rules

		sudo iptables -F

- defining iptables

		sudo iptables --append <chain> --in-interface <interface> --protocol <protocol(tcp/udp)> --source <source-ip/hostname> --dport <destination port number> --jump <target>

	- <chain>
			INPUT is an ingoing traffic
			OUTPUT is an outgoing traffic
			FORWARD is a forward traffic (man-in-the-middle, router)
	- <interface> for localhost is **lo**
	- <target> is a policy specification (ACCEPT, DROP, REJECT, QUEUE, RETURN) 

## Useful options 

- Makes sure First TCP segment when initiating a connection is/has a SYN bit (first segment of TCP handshake)
	
		iptables -A INPUT -p tcp ! --syn --sport 22 -j ACCEPT

- Add the -s (--source) options to apply rule only for a specific IP/hostname(X.X.X.X) or some subnet (X.X.X.X/X)

		--source <IP/hostname/subnet>

- Specify multiple ports at a time, by adding

		--match multiport (e.g. --dport 22,80,443 without spaces!)
	
- Apply rule to all IPs except for(watchout for the exclamation point!):
	
		iptables -t filter -I INPUT -p tvp ! -s <IP> -j DROP

## Additional match modules

### state

- Usage:

		-m state --state <STATE-OPTION>

- State options:

		NEW - The matching packet is either creating a new connection or is part of a two-way connection not previously seen.
		ESTABLISHED - A packet that is part of an existing connection
		RELATED - A packet that is requesting a new connection but is part of an existing connection. For example, FTP uses port 21 to establish a connection, but data is transferred on a different port (typically port 20).
		INVALID -  A packet that is not part of any connections in the connection tracking table.

## Tips

First I found it really difficult to grasp whether I should use --dport option or --sport.
For example if you have a command (allow outgoing http traffic):

Should I use:

	iptables -A OUTPUT -p tcp --sport 80 -j ACCEPT	

or:

	iptables -A OUTPUT -p tcp --dport 80 -j ACCEPT	

The key misconception I had was that whether am I connecting to server through port 80 or am I connecting to server on port 80. Obviously, the correct is latter, but that was not so obvious to me back then.
