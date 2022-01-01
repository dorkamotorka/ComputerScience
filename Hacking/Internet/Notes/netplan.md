# Netplan

The following was tested using VM Virtual Box, where 8 Virtual Ethernet card are available to connect to internet, therefore we are using the **ethernets** tag to list them.

## How to configure interface to use DHCP

    network:
      version: 2
      ethernets:
        <interface (e.g. enp0s3)>:
          dhcp4: true
          dhcp-identifier: mac

## How to configure interface to use static IPs

    network:
      version: 2
      ethernets:
        <interface (e.g. enp0s3)>:
          addresses: [<IP-ADDRESS(without gateway) e.g. 10.1.0.2>/<NETMASK e.g. 16>]
          gateway4: <GATEWAY-IP e.g. 10.1.0.1>
          nameservers:
            addresses: [8.8.8.8] # Google DNS Server
