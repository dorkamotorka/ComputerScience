# SSH Port Forwarding

## Local Port Forwarding

Imagine that you'd like to access a service on a remote machine, but that service is configured to allow localhost connections only. To alleviate such limitations, we can use SSH to create a tunnel between our machine and the machine running the service and then access the service as if it was running on our machine.

The SSH will instantiate a local network socket that will forward all traffic to the service socket on the remote machine. To the service on the remote machine, the tunnel will be completely transparent: all requests that are sent through the tunnel will appear to the service as normal requests originating from localhost.

To make this example more concrete, let's configure the Apache webserver on the ssh-server to allow localhost connections only. Open file **/etc/apache2/sites-available/000-default.conf** and add the following snippet that limits the access to the Apache served pages:

    <Directory /var/www/html>
        Require ip 127.0.0.1/8
    </Directory>
    
Reload the Apache configuration file with:

    sudo service apache2 reload

If you access the apache on the server, the access should be granted. Try by running:

    curl localhost

If you do the same on the ssh-client (you have to replace localhost with ip of the server), you should get an HTML page saying that the access is not allowed.
Now, let's circumvent this access control by accessing the Apache server from the ssh-client with the help of a SSH tunnel.

On ssh-client, set up a tunnel by issuing:

    ssh -L 127.0.0.1:8080:127.0.0.1:80 -N $SERVER

The -L switch denotes local port-forwarding and the -N prevents executing remote commands; this is useful for only setting up forwarded ports and not actually running terminal on the remote machine.

After you have run the command, open a new terminal (the current one is ssh'd to the server), and run:

    curl localhost:8080
    
Alternatively, open Firefox in Private browsing (ctrl+shift+p) mode and navigate to http://localhost:8080; private browsing mode is needed to disable caching, which may interfere with testing.
You are now able to access the Apache2 on the server machine, as if you were physically on the server machine. The only catch is that you have to explicitly specify the tunneled port number.

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
