## Proxy Chains:

- Pros:
	- simple to use and good for legal stuff

- Cons: 
	- delay(more proxies/more delay)
	- Passing source IP of user through proxies(each proxy can see from which proxy message came and to which it is sent)
	- Public proxies full of attackers
	- Time & Effort to find working/secure proxies
	- One bad proxy means bad proxy chain
	- Proxies ussualy only browser

## TOR 

- Pros: 
	- bouncing connection
	- Open source

- Cons: 
	- delay (TOR network)
	- last connection security depending upon website(http)
	- Government watchful of TOR users
	- Companies blacklist endpoint TOR server

## VPN

- Pros: 
	- no delay(tunnel between two endpoint devices)
	- Good for geolocation restricted media
	- Ideal for F2F file sharing

- Cons:
	- may store logs and data (VPN provider), required by law in some countries
	- Might use bad encryption
	- Tor through VPN: (pc -> vpn -> tor -> server)

- Pros: 
	- ISP may block usage of TOR and this way it cannot detect you are using it

- Cons:
	- TOR exit point can lead government back to VPN server and catch you by obtaining logs from VPN provider

## VPN through TOR (pc -> vpn -> tor -> vpn -> server)

- Pros:
	- Really secure (the best option among mentioned)
- Cons:
	- VPN needs to be configured to work with TOR
	- slow because of TOR
- Options:
	- AirVPN and BolehVPN 
