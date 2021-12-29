#!/bin/sh

DESC="netfilter/iptables firewall on $HOSTNAME"

# Make sure only root can run our script
if [ $(/usr/bin/id -u) -ne 0 ]
then
   echo "This script must be run as root" 1>&2
   exit 1
fi

# Exit immediately if a command exits with a non-zero status.
set -e

# Disable IPv6
grep -qxF 'net.ipv6.conf.all.disable_ipv6 = 1' /etc/sysctl.conf || echo 'net.ipv6.conf.all.disable_ipv6 = 1' >> /etc/sysctl.conf
grep -qxF 'net.ipv6.conf.default.disable_ipv6 = 1' /etc/sysctl.conf || echo 'net.ipv6.conf.default.disable_ipv6 = 1' >> /etc/sysctl.conf
grep -qxF 'net.ipv6.conf.lo.disable_ipv6 = 1' /etc/sysctl.conf || echo 'net.ipv6.conf.lo.disable_ipv6 = 1' >> /etc/sysctl.conf
sudo sysctl -p

d_start() {
   # No forwarding - for routers
   echo 0 > /proc/sys/net/ipv4/ip_forward

   # Enable TCP SYN Cookie Protection
   echo 1 > /proc/sys/net/ipv4/tcp_syncookies

   # Enable ping broadcast address Protection
   echo 1 > /proc/sys/net/ipv4/icmp_echo_ignore_broadcasts

   # Don't send ICMP Redirect Messages
   for f in /proc/sys/net/ipv4/conf/*/send_redirects; do
       echo 0 > $f
   done

   # Disable ICMP Redirect Acceptance
   for f in /proc/sys/net/ipv4/conf/*/accept_redirects; do
       echo 0 > $f
   done

   # Remove any existing rules from all chains
   sudo iptables --flush
   sudo iptables -t nat --flush
   sudo iptables -t mangle --flush

   # Disable all chains, therefore only traffic that we allow is passed through
   sudo iptables --policy INPUT DROP
   sudo iptables --policy OUTPUT DROP
   sudo iptables --policy FORWARD DROP

   # Delete any user-defined chains
   sudo iptables -X
   sudo iptables -t nat -X
   sudo iptables -t mangle -X

   # Reset all counters to zero
   sudo iptables -Z

   ### Allow all trafic on localhost
   sudo iptables -A INPUT -i lo -j ACCEPT
   sudo iptables -A OUTPUT -o lo -j ACCEPT

   # ESTABLISH-RELATED trick: Allow all incoming packets that belong to ESTABLISHED or RELATED connections.
   sudo iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT
   sudo iptables -A OUTPUT -m state --state ESTABLISHED,RELATED -j ACCEPT
   # From here onwards, we can add incoming firewall exceptions using only the NEW state

   #################### HERE YOU CAN ADD YOUR FIREWALL RULES ######################################
   # Allow incomming connections to local SSH server
   #sudo iptables -A INPUT -p tcp --dport 22 -m state --state NEW -j DROP

   # Allow only ssh between machines on a local network
   #sudo iptables -A INPUT -p tcp --dport 22 -m state --state NEW --source 192.168.1.0/24 -j ACCEPT
   #sudo iptables -A OUTPUT -p tcp --dport 22 -m state --state NEW --destination 192.168.1.0/24 -j ACCEPT

   # Disable connection to facebook.com
   #sudo iptables -A OUTPUT -p tcp -m state --state NEW --destination 157.240.236.35 -j DROP

   # Allow outgoing ping requests
   #sudo iptables -A OUTPUT -p icmp --icmp-type echo-request -m state --state NEW -j ACCEPT
   #sudo iptables -A INPUT -p icmp --icmp-type echo-reply -m state --state NEW -j ACCEPT
   # Allow incoming ping requests
   #sudo iptables -A INPUT -p icmp --icmp-type echo-request -m state --state NEW -j ACCEPT
   #sudo iptables -A OUTPUT -p icmp --icmp-type echo-reply -m state --state NEW -j ACCEPT

   ##################### HERE YOU CAN ADD YOUR FIREWALL RULES ##################################
}

d_stop() {
   ####################
   ### Default policy
   ####################
   # Disable INPUT
   iptables --policy INPUT DROP
   # Disable OUTPUT
   iptables --policy OUTPUT DROP
   # Disable FORWARD
   iptables --policy FORWARD DROP
   ###################
   ### Clear old rules
   ###################

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

   ####################
   ### Set up new rules
   ####################
   # Enable INPUT
   iptables --policy INPUT ACCEPT
   # Enable OUTPUT
   iptables --policy OUTPUT ACCEPT
   # Enable FORWARD
   iptables --policy FORWARD ACCEPT
}

case "$1" in
  start)
	echo -n "Starting $DESC"
	d_start
	echo "."
	;;
  stop)
	echo -n "Stopping $DESC"
	d_stop
	echo "."
	;;
esac

# TODO: OTA update
# Caveat: For multiple robots system all ports needs to open in ROS1

exit 0
