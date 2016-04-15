# ChatRoom
In this assignment, you will use socket programming to simulate a chatroom application. You need to implement:

- A TCP server: it Receives and stores all messages from producer clients. When a new listener client comes, it first sends all stored message to the client and keeps the client updated When a new message is received.

- A TCP client Producer: it first Identifies itself as a producer client to the server and then sends messages.

- A TCP listener client: it first Identifies itself as a listener client to the server, accepts all stored messages from the server, and then Continuously Receives messages from the server.
