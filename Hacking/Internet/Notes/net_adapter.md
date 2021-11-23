# Network Adapter

Network Adapter, Network Interface Card(NIC), Network Card, this is all the same..

An extension board that enables a device to connect to Internet.

It has many forms such as USB dongle, Antenna, ethernet Jack..


## Modes 

### NAT

Is a protocol that translates local IP address to router IP(public one). 
Absolutely neccesary when making a request to the internet since the reply needs to come back to the local machine.
But since local machine is not seen on public internet, reply is send to router which that using NAT figures out back which local machine made a request for this reply.

In case of VMs, if it requires an internet connection NAT(engine) is set, which acts as a router between physical host and a VM.
Disadvantage is obvious, the physical hosts cannot connect to VM since it is behind NAT(a router) and they cannot see its IP.(Same a public internet cannot see your local network, only the router)
This can be solved by setting up port forwarding.
For each VM a separate NAT engine is set, since the principle of VMs is to provide isolation, which also means that VMs between themself should not be seen.


### NAT Network

Creates a virtual local (NAT) network of VMs that are configured to be added to this NAT Network, this way they can access and discover each other as on a normal local network.


### Bridged Adapter

This mode is used for connecting the virtual network adapter of a VM to a physical network to which a physical network adapter of the VirtualBox host machine is connected. 
A VM virtual network adapter uses the host network interface for a network connection. 
Put simply, network packets are sent and received directly from/to the virtual network adapter without additional routing. 
A special net filter driver is used by VirtualBox for a bridged network mode in order to filter data from the physical network adapter of the host.
This network mode can be used to run servers on VMs that must be fully accessible from a physical local area network.

### Internal Network
TODO

### Host-only Adapter
TODO
