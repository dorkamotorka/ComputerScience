# This should all be set to 0, otherwise we can redirect traffic?
grep [01] /proc/sys/net/ipv4/conf/*/accept_redirects | egrep "default|all"
grep [01] /proc/sys/net/ipv4/conf/*/send_redirects | egrep "default|all"

# Enable broadcast echo Protection
cat /proc/sys/net/ipv4/icmp_echo_ignore_broadcasts

# Enable TCP SYN Cookie Protection
cat /proc/sys/net/ipv4/tcp_syncookies

# Does security(firewall) rules apply also to IPv6
cat /proc/sys/net/ipv6/conf/all/disable_ipv6
