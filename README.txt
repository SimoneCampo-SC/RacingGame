
INTRODUCTION
The Racing Game project consists of three parts.

The first part focuses on implementing a graphical user interface (GUI) using JFrame and JPanel. It involves animating the movement of two cars that can rotate 360 degrees. The MyFrame class sets the boundaries and graphical elements of the screen, while the MyPanel class loads and paints the cars using different images stored in arrays. The animation is achieved by iterating through the array of images and repainting the component using a timer.

In the second part of the assignment, a complete racing game is developed where two players can control the cars on a racetrack and compete to reach the finish line. The GameFrame class extends JFrame and serves as the main window for the game. The GamePanel class manages the game's GUI and user input through the keyboard. It includes methods for drawing the racetrack, cars, legends, and statistics. The Game class models the game's core instructions, including collision detection, car movement, and game win/loss events. The RaceTrack class defines the attributes of the race track, such as the grass, outer and inner edges, and checkpoints. The Car class models the car objects and handles their movement and collisions.

The third part of the assignment involves creating a client-server application. Two instances of the client can connect to the server using TCP handshake protocol and control their own cars. The ClientData class stores information about the player and opponent's cars, while the ServerData class stores information about the player one and player two's cars. The ClientThread class allows the client to connect to the server and communicate using TCP. The TCPServer class initializes a server that can accept multiple clients and creates a thread for each connected client. The TCPClientHandler class handles communication between the server and clients.

Overall, the project involves implementing a racing game with a graphical interface, user input controls, car movement and collision detection, and client-server communication for multiplayer functionality.

PART 1 & Part 2
To run the part 1 and part 2 of the assignment you only need to run a single instance of Main class

PART 3
To run the server and the client app the following steps must be done in the following order:
	1) Enable the TCPClient to run multiple instances
	2) Run the TCPServer using Main.java
        3) Run the TCPClient using Main.java twice