# FREERadius

- AAA Server (Authentication, Authorization, Accounting)
- Uses UDP/1812-1813 ports

## Authentication 

Authentication is established in two messages:

![image](https://user-images.githubusercontent.com/48418580/147924840-99c1e54d-9b50-4ee1-8605-a50b99f671a7.png)

### Message 1 ~ Access Request

User sends his user name and encrypted password to RADIUS server.

![image](https://user-images.githubusercontent.com/48418580/147924915-d0b2c5e4-41d8-4ab5-92a6-813b849808a6.png)

### Message 2 ~ Access Accept

RADIUS either accepts or rejects access to the resource.

![image](https://user-images.githubusercontent.com/48418580/147924961-8e35655b-122a-4399-a775-c8a71e180f89.png)

WireShark data can be accessed on [CloudShark](https://www.cloudshark.org/captures/e33e64d14507).

## Terminology

- **Authentication** provides a way of identifying a user, typically by having the user enter a valid user name and valid password before access is granted
- **Authorization** determines whether the user has the authority to issue certain commands
- **Accounting** measures the resources a user consumes during access
- **AVP** (Attribute-Value Pair) is a part of a radius message/packet (most information between RADIUS client and server is exchanged as attributes)
