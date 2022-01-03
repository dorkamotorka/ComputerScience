# VPN

## Types of VPN

- IPSec
- SSLVPN
- PPTP
- L2TP

The following guide will explain and setup a IPSec VPN.

## IPSec VPN

is a protocol suite that provides security at the network layer(protects IP packets).

The entire process of setting up IPSec VPN consists of five steps:
- Initiation (something that triggers the need of creating secure channels)
- IKE phase 1 (SA negotiation to build ISAKMP secure tunnel)
- IKE phase 2 (Within ISAKMP tunnel we build IPSec tunnel)
- Data transfer (communication between endpoint users)
- Termination (shutting down the tunnel)

Now you have an idea of the basics of IPsec, let’s take a closer look at each of the different components.

### Protocols

It provides the following protocols:

- Authentication Headers (AH) 
  - provides integrity and authentication, but NO confidentiality
  - can detect replay attacks
  - transport and tunnel mode
  - mostly DEPRECATED
– Encapsulating Security Payloads (ESP)
  - provides data integrity(optional) and confidentiality of IP packets
  - transport and tunnel mode (In transport mode, the IP headers are not protected)
– Security Associations (SA)
  - association that specifies security properties(encryption, integrity etc.) between two hosts
  - single SA protects data in one directions (host1 encrypts, host2 decrypts), therefore there are ussualy always two SA(Outbound and Inbound)
  - Each SA is uniquely by Security Parameter Index(SPI), protocol type(ESP or AH) and Partner IP

### AH vs ESP (Which one to use?)

- AH(in both modes) is not suitable when you have a NAT/PAT between clients, because IP ports and addresses are included when calculating the hash of a packet (therefore integrity check would fail) 
- ESP+AH could therefore never successfully traverse a NAT/PAT

Your best bet is to use **ESP+Integrity in Tunnel mode**. (Integrity is optionally included in ESP)

Extremelly good article I recommend reading: http://www.unixwiz.net/techtips/iguide-ipsec.html

### Modes

It can operate in two modes:
- **Transport mode** 
  - Add protection to the original packet
  - Normally between two network hosts
- **Tunneling mode**
  - Creates a new IP packet that encapsulates the original one
  - Normally between two network gateways: **used to set up VPNs**

### IKE

The following guide provides a great introduction and a detailed overview of IKE(v1): https://networklessons.com/cisco/ccie-routing-switching/ipsec-internet-protocol-security
I higly recommend reading it before proceeding, since the following protocol build upon it.

The following steps apply to IKEv2.

#### IKE(v2) Phase 1

IKEv2 establishes a secure channel exchanging only 4 messages between hosts.

![image](https://user-images.githubusercontent.com/48418580/147893031-db2eb8d1-8f7f-4555-878e-f631c29135d3.png)

- Message 1 ~ Initiator Request

![message1](https://user-images.githubusercontent.com/48418580/147893435-b3088c68-9271-4df9-95e0-280ebcba1ff4.png)

Sends the Initiator SPI, IKE version (2.0), SA(encryption, integrity, authentication, DH group) proposal, nonce and key exchange data(public key) for DH.
Here it can also be seen that ISAKMP protocol communicates through port 500.

- Message 2 ~ Responder Response

![message2](https://user-images.githubusercontent.com/48418580/147893569-1758024f-f74d-4658-99cf-4f64990d5f52.png)

Reponses with Responser SPI, IKE version (2.0), SA proposal, nonce and key exchange data(public key) for DH.

- Message 3 ~ Initiator Request

![message3](https://user-images.githubusercontent.com/48418580/147893632-f2c2377d-cab7-4eb9-8019-a496b2a0e8fe.png)

Encrypted initiator identification and authentication data using shared secret (agreed with DH) 

- Message 4 ~ Responder Response

![meesage4](https://user-images.githubusercontent.com/48418580/147893677-8681ecb8-8f20-4511-bd7f-8617aeb297d8.png)

Encrypted responder identification and authentication data using shared secret (agreed with DH) 

#### IKE(v2) Phase 2

The IKE phase 2 tunnel (IPsec tunnel) will be used to protect the exchanged data. It negotiatiates a connection parameters in details:

- IPsec Protocol: do we use AH or ESP?
- Encapsulation Mode: transport or tunnel mode?
- Encryption: what encryption algorithm do we use? DES, 3DES or AES?
- Authentication: what authentication algorithm do we use? MD5 or SHA?
- Lifetime: how long is the IKE phase 2 tunnel valid? When the tunnel is about to expire, we will refresh the keying material.
- (Optional) DH exchange: used for PFS (Perfect Forward Secrecy).

The negotiation happens within the protection of our IKE phase 1 tunnel so we can’t see anything.

### Implement 

TODO: Setup 4 machines

### Configuration

All conn and ca sections inherit the parameters defined in a conn %default or ca %default section, respectively.
strongSwan's /etc/ipsec.conf configuration file consists of three different section types:

- **config setup** defines general configuration parameters

Generally it can be left blank. Available parameters are documented here: https://wiki.strongswan.org/projects/strongswan/wiki/ConfigSetupSection

- **ca (name)** defines a certification authority

Generally it can be left blank. Available parameters are documented here: https://wiki.strongswan.org/projects/strongswan/wiki/CaSection

- **conn (name)** defines a connection

The most common parameters to setup for a connection are:

  - IKE parameters:

        ikelifetime (how long the keying channel of a connection should last before renegotiation), ussualy set to 60m
        keylife (how long a particular instance of a connection (a set of encryption/authentication keys for user packets) should last, from successful negotiation), ussualy set to 20m
        rekeymargin (how long before connection expiry or keying-channel expiry should attempt to renegotiate), ussualy set to 3m
        keyingtries (how many attempts (<number> | %forever) should be made to negotiate a connection)
        keyexchange (method of key exchange (ikev1 | ikev2))

  - Authentication

        authby (how the two gateways should authenticate each other)

  - Cipher suites
        
        ike (encryption-integrity(-PRF)-dhgroup(!)) - proposed in Phase 1
        NOTE: exclamation point(!) can be added to restrict a responder to only accept specific cipher suites
        esp - used in Phase 2
        ah - used in Phase 2

Available cipher suites: https://wiki.strongswan.org/projects/strongswan/wiki/IKEv2CipherSuites

Commercial National Security Algorithm (CNSA) Suite: 

    ike=aes256gcm16-prfsha384-modp3072
    esp=aes256gcm16-modp3072
    
- Connection specific details:

      - left can be thought of as local, right can be thought of as remote:
        
        left|rightsubnet (subnet with mask)
        left|rightfirewall (turn OFF firewall rules between connected networks)
        left|rightid (identifier of gateway)
      
      Configuration of left and right must match on both end!
      
      - Other:
      auto (operation done automatically at IPsec startup)

#### Example of full config

- Gateway 1
  - /etc/ipsec.conf

        config setup

        conn %default
                ikelifetime=60m
                keylife=20m
                rekeymargin=3m
                keyingtries=1
                keyexchange=ikev2
                ike=aes256-sha512-modp2048!
                esp=aes256gcm128-modp2048!
                authby=secret

        conn net-net
                leftsubnet=10.1.0.0/16
                leftfirewall=yes
                leftid=@hq
                right=$BRANCH_IP
                rightsubnet=10.2.0.0/16
                rightid=@branch
                auto=add

  - /etc/ipsec.secrets
    
        @hq @branch : PSK "secret"
        
- Gateway 2
  - /etc/ipsec.conf

        config setup

        conn %default
                ikelifetime=60m
                keylife=20m
                rekeymargin=3m
                keyingtries=1
                keyexchange=ikev2
                ike=aes256-sha512-modp2048!
                esp=aes256gcm128-modp2048!
                authby=secret

        conn net-net
                leftsubnet=10.2.0.0/16
                leftid=@branch
                leftfirewall=yes
                right=$HQ_IP
                rightsubnet=10.1.0.0/16
                rightid=@hq
                auto=add

  - /etc/ipsec.secrets
    
        @hq @branch : PSK "secret"
        
Don't forget to restart IPsec afterwards:

    sudo ipsec restart
      
To run/establish VPN link between gateway, invoke 

    sudo ipsec up net-net 
    
on either routher (but only on one).

To print out SA database, where agreements about SPI, encryption, integrity is stored:

    sudo ip xfrm state
    
Simila debug information is available typing:

    sudo ipsec statusall
    
    
### Authentication with PKI Certificates

In order to replace PSK with PKI certificate, I had done the following:
- generate a private key (on an arbitrary host)

      pki --gen > caKey.der
      
This by defualt generates a 2048 bit RSA key (use --type and/or --size to specify other key types and lengths).
- self-sign a CA certificate using generated key

      pki --self --in caKey.der --dn "C=CH, O=strongSwan, CN=strongSwan CA" --ca > caCert.der
      
- Then for each peer(VPN gateway) generate an individual private key and issue a matching certificate using the CA create in the step above

      pki --gen > peerKey.der
      
      ipsec pki --pub --in peerKey.der | ipsec pki --issue --cacert caCert.der --cakey caKey.der --dn "C=SL, O=FRI-UL, CN=branch" --san @branch > branchCert.der
      
Note that, this requires from us to transfer caCert.der and caKey.der to the host. And also make sure you change the identifier(in my case @branch) to the identifier of the machine.
- Afterwards move generated files to corresponding directories, where VPN system can find them

      mv peerKey.der /etc/ipsec.d/private/
      mv caCert.der /etc/ipsec.d/cacerts/
      mv branchCert.der /etc/ipsec.d/certs/
      
- Then you can include/use it in **/etc/ipsec.conf**:

        config setup

        conn %default
                ikelifetime=60m
                keylife=20m
                rekeymargin=3m
                keyingtries=1
                keyexchange=ikev2
                ike=aes256-sha512-modp2048!
                esp=aes256gcm128-modp2048!
                # authby=secret # REMOVE THIS - WE WILL AUTH WITH CERTS

        conn net-net
                leftsubnet=10.2.0.0/16
                leftcert=branchCert.der # ADD YOUR CERT HERE
                leftid=@branch
                leftfirewall=yes
                right=$HQ_IP
                rightsubnet=10.1.0.0/16
                rightid=@hq
                auto=add
      
and in **/etc/ipsec.secrets**:
        
          : RSA peerKey.der # THERE IS A SPACE BEFORE : !
      
### Terminology

- **keying channel** (secure channel established in Phase 1 IKE)
- **data channel** (channel established in Phase 2 IKE)
- **Pseudo-Random function** is indistinguishable from a random function, that is, given any x, no adversary can predict F(x)

Many more IPSEC configurations: https://www.strongswan.org/testresults4.html

## References

https://networklessons.com/cisco/ccie-routing-switching/ipsec-internet-protocol-security
https://www.freeswan.org/freeswan_snaps/CURRENT-SNAP/doc/ipsec.html
https://www.csoonline.com/article/2117067/data-protection-ipsec.html
