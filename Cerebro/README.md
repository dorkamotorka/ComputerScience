# Cerebro 

Yes this is a fictional room seen in X-men, but no it's not about it.
This docs is intended to provide security information oh how to protect your endpoint Linux system.

## Update your system

The absolute minimum you can do to secure your system is to make sure it is up-to-date, meaning it has latest software features.

That's done by typing in terminal:

	sudo apt update & sudo apt upgrade

Since it is really likely you will forgot to do so it is possible to automate it by installing:

	sudo apt unattended-upgrades

and running:
	
	dpkg-reconfigure --priority=low unattended-upgrades

Choose 'Yes' in the pop-up window.

NOTE: That automated update in not always recommended since it relies on all Ubuntu packages delivered to be fail-safe, which is not always the case and it might brake your system. 

NOTE: Major downside of apt updated packages is that for some the changes only take place once system is rebooted, which is also the case for automated updates. If the file **/var/run/reboot-required** exists the system requires reboot.

### Updating multiple systems at once

Before updating multiple systems by using only a single command, we need to setup SSH keys in order for your local machine to communicate with other system (you want to update) without the password. 
More on that can be found here: https://www.cyberciti.biz/faq/how-to-set-up-ssh-keys-on-linux-unix/

Also you need to add to Ansible/hosts file IP addresses or hostnamem (in case you have DNS figured out) of the system you want to update.

After that run:

	ansible-playbook -i hosts update.yml

## SSH Hacks

### SSH Asymetric Authentication

Passwords authentication are old. Even a 30-digit password is useless compared to using a public-key.
How it works is basically you generate a pair of two keys - private and public.
Public key is shared and resides in the remote system, while private key should be securely stored in the local machine.
The public key is used to encrypt data that can only be decrypted with the private key. 
The public key can be freely shared to a remote machine, because, although it can encrypt for the private key, there is no method of deriving the private key from the public key.

To get started, login to the local machine you will use to access the remote server and run:

	ssh-keygen -t (rsa|dsa|ecdsa|ed25519) (optional: -b 4096 -a 100)

NOTE: You can generate ssh keys using different encryption algorithms such as RSA, DSA, ECDSA, EdDS but is it RSA and EdDSA that provide the best security and performance. 
In the command above we are using RSA encyption with key length 4096 bits. 
'-a 100' flag is optional but it makes it harder to brute-force since the key is ran through key derivation function 100-times.

This command will generate an ssh key pair in the ~/.ssh directory. One denoted as id_rsa (private) and id_rsa.pub (public).
Public key than needs to be shared to the remote machine we want to access. 
We can do that by:

	ssh-copy-id [USER_ON_SERVER]@[SERVER_IP]

This command copies your key to a remote computer(authorized keys list) and links it to a specific user.
Now you should be able to ssh to remote machine without password.

But this only enables public-key authentication for you PC, while other clients will still be asked for username and password. 

If we want "this extra bit" of security we can disable password authentication.
Open file **/etc/ssh/sshd_config** and modify:

	PasswordAuthentication no

### Lockdown Logins

You can even improve your ssh even more. SSH config is done in **/etc/ssh/sshd_config**.

#### Change default port server is listening for ssh client request. 

We want to do that to provide an additional security especially for Brute Force Attacks(BF). In case of changed defualt SSH port, hacker has to try different ports on trial and error base(BF). 
Find line that begin with **Port** and changed it to an arbitrary port (Make sure it is not already used by some other process).
NOTE: Consequence of that will be that each time you ssh to your server you will need to use the following form:

	ssh [USER]@[SERVER_IP] -p [PORT_NUMBER] 

#### Disable root login

By default root logins through ssh are enables, which exposes yet another user account that can be attack using brute-force. And since you can log in as a normal user and then login to root account thereafter it does not make sense to expose root.
Root ssh login can be disabled by setting the line in sshd_config like:

	PermitRootLogin no

#### Disable X11 Forwarding


## Firewall

Setting up firewall is easy, we will use Uncomplicated Firewall(UFW) tool. 
Note that UFW works with iptables, but there is a new configuration called nftables. Since nftables is a fairly new thing and it experienced some issues in Ubuntu 20.04 still, we will proceed with ufw (iptables). 
For the curious ones, porting to nftables is rather straight forward since it is compatible with iptables.
To set up firewall, install:

	sudo apt install iptables
	sudo apt install ufw

and run: 
	
	sudo ufw enable

Generally you can disable all incoming and outgoing traffic but that's not what you ussualy want if you are running an application on the system.
The most minimal thing you probably should do is allow ssh traffic:

	sudo ufw allow ssh

Here is a summed up list of ports we at the Ubiquity are using and should be non-blocking:

- ssh
- 11311(tcp/udp) (ROS Master)
- 9090 (ros_bridge websocket for Web App)
	
