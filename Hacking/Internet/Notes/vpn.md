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
  - provides origin authenticity, integrity and confidentiality of IP packets
  - transport and tunnel mode (In transport mode, the IP headers are not protected)
– Security Associations (SA)
  - association that specifies security properties(encryption, integrity etc.) between two hosts
  - single SA protects data in one directions (host1 encrypts, host2 decrypts), therefore there are ussualy always two SA(Outbound and Inbound)
  - Each SA is uniquely by Security Parameter Index(SPI), protocol type(ESP or AH) and Partner IP

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

- Message 1 ~ Initiator Request

Sends the Initiator SPI, IKE version (2.0), SA(encryption, integrity, authentication, DH group) proposal, nonce and key exchange data(public key) for DH.

- Message 2 ~ Responder Response

Reponses with Responser SPI, IKE version (2.0), SA proposal, nonce and key exchange data(public key) for DH.

- Message 3 ~ Initiator Request

Encrypted initiator identification and authentication data using shared secret (agreed with DH) 

- Message 4 ~ Responder Response

Encrypted responder identification and authentication data using shared secret (agreed with DH) 

#### IKE(v2) Phase 2

The IKE phase 2 tunnel (IPsec tunnel) will be actually used to protect user data.

https://www.csoonline.com/article/2117067/data-protection-ipsec.html
