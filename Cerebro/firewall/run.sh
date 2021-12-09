#!/bin/sh

# Make sure only root can run our script
if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

# Exit immediately if a command exits with a non-zero status.
set -e

# Disable IPv6
tee -a /etc/sysctl.conf <<EOF
net.ipv6.conf.all.disable_ipv6 = 1
net.ipv6.conf.default.disable_ipv6 = 1
net.ipv6.conf.lo.disable_ipv6 = 1
EOF

sudo sysctl -p

# Disable all chains, therefore only traffic that we allow is passed through
iptables --policy INPUT DROP
iptables --policy OUTPUT DROP
iptables --policy FORWARD DROP

# Remove any existing rules from all chains
iptables --flush
iptables -t nat --flush
iptables -t mangle --flush

# Delete any user-defined chains
iptables -X
iptables -t nat -X
iptables -t mangle -X

# Reset all counters to zero
iptables -Z

### Allow all trafic on localhost
iptables -A INPUT -i lo -j ACCEPT
iptables -A OUTPUT -o lo -j ACCEPT

# ESTABLISH-RELATED trick: Allow all incoming packets that belong to ESTABLISHED or RELATED connections.
# From here onwards, we can add incoming firewall exceptions using only the NEW state
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT
iptables -A OUTPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# Allow incomming connections to local SSH server
iptables -A INPUT -p tcp --dport 22 -m state --state NEW -j ACCEPT

# Allow outgoing ping requests
iptables -A OUTPUT -p icmp --icmp-type echo-request -m state --state NEW -j ACCEPT
iptables -A INPUT -p icmp --icmp-type echo-reply -m state --state NEW -j ACCEPT
# Allow incoming ping requests
iptables -A INPUT -p icmp --icmp-type echo-request -m state --state NEW -j ACCEPT
iptables -A OUTPUT -p icmp --icmp-type echo-reply -m state --state NEW -j ACCEPT

# TODO: OTA update
# TODO: All things need to be run on localhost, for multiple robots all ports needs to open in ROS1

exit 0
