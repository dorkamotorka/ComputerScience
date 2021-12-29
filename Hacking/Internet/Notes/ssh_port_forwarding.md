# SSH Port Forwarding

## Local Port Forwarding

## Remote Port Forwarding

Imagine you have three machines: client, middle and server machine. Client cannot connect to server machine, either it is not on the same network, or due to firewall rules etc.
But it is possible for middle machine to enable client machine to connect to server machine.
By invoking command on middle machine:

    ssh -R <PORT-ON-MIDDLE-MACHINE>:<SERVER-IP>:<SERVER-PORT> -N <MIDDLE-MACHINE-USER>@<MIDDLE-MACHINE-IP>

and setting:
  
    AllowTcpForwarding yes
    GatewayPorts yes
    
in **/etc/ssh/sshd_config**. Dont forget to restart the sshd service using:

    sudo service sshd restart
    
in order for changes to take place.
I need to provide some comment about the command, since when I first saw I was completely confused.
- -R stands for reverse port forwarding
- -N flag only provides port forwarding but does not login to server machine after you invoke that command
- **MIDDLE-MACHINE-USER@MIDDLE-MACHINE-IP** at the end of command is always the machine that is enabling the port forwarding in this case middle machine (the same analogy goes for local port forwarding)
- about **PORT-ON-MIDDLE-MACHINE:SERVER-IP:SERVER-PORT** you need to think like, where does the tunnel starts and where does it end

and by invoking:
  
    ssh <MIDDLE-MACHINE-USER>@<MIDDLE-MACHINE IP> -p <PORT-ON-MIDDLE-MACHINE>
   
this will redirect you straight to the server command prompt (through the ssh tunnel).
  
Pitfal: While I was trying to enable remote port forwarding on a middle machine, I could not make it work to port forward middle machine port 22 to server machine port 22.
So the following command did not work:

    ssh -R 22:192.168.1.38:22 -N 127.0.0.1
    
While this command worked:

    ssh -R 2000:192.168.1.38:22 -N 127.0.0.1 
    
Port number 2000 was randomly chosen to avoid confusion(you should only make sure the port number is above 1024. Lower port number require super user privileges.)
Note that **PasswordAuthentication** needs to be set to **yes** on Server machine, otherwise portforwarding fails.
