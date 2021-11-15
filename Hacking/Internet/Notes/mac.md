# Why can't we just use local IPs on the local network instead of MAC addresses?

- Don't assume that there is only one protocol for each layer. Layer-2 has many protocols, some of which use MAC addresses, and some which don't. Of those that use MAC addresses, some use 48-bit MAC addresses, and some use 64-bit MAC addresses.
- There are also multiple layer-3 protocols. IPv4, the most used, but not the only, layer-3 protocol, uses 32-bit addresses, but it is being replaced with IPv6, which uses 128-bit addresses.
- You don't want to replace or upgrade all your layer-2 devices each time you want to run a different layer-3 protocol
- DHCP to match dynamic IPs to fixed MACs(MAC as a reference)
