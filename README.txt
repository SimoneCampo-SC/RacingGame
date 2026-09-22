# Racing Game

A Java racing-game project developed in three stages, progressing from Swing-based animation to local two-player gameplay and finally to TCP client-server multiplayer.

The repository is preserved as an earlier engineering project and is structured to show how the application evolved as new requirements were introduced.

## Project progression

### Part 1 — Swing animation

The first stage focuses on graphical rendering and animation with Java Swing.

Two car sprites are displayed in a `JFrame`/`JPanel` interface and rotated through 16 directional images in 22.5-degree increments. A Swing `Timer` drives repeated repainting of the panel.

Key concepts:

- Java Swing
- `JFrame` and `JPanel`
- sprite-based rendering
- timer-driven animation
- resource loading

### Part 2 — Local racing game

The second stage develops the animation prototype into a complete local two-player racing game.

It introduces:

- keyboard-controlled movement for two cars;
- speed and direction state;
- track boundaries and collision handling;
- car-to-car collision detection;
- sequential checkpoints and race completion;
- engine, crash, win and loss audio;
- a dedicated game-update thread;
- on-screen controls and speed information.

The red car and yellow car are controlled independently from the same keyboard.

### Part 3 — TCP multiplayer

The final stage restructures the game into separate `Client`, `Server` and `Shared` packages and introduces multiplayer communication over TCP.

Two client instances connect to a server running on `localhost:4000`. The server assigns each client a car, waits for both players to be ready and exchanges serialised `Car` objects so that each client can maintain the opponent's latest state.

The multiplayer implementation includes:

- TCP sockets;
- client/server architecture;
- per-client server handlers;
- Java threads;
- object serialisation;
- shared game models and configuration;
- synchronisation of player state;
- a waiting lobby before both clients are ready.

## Repository structure

```text
Part 1/
└── RacingGame/
    └── src/        Swing animation prototype

Part 2/
└── RacingGame/
    └── src/        Local two-player racing game

Part 3/
└── RacingGame/
    └── src/
        ├── Client/
        ├── Server/
        └── Shared/
```

The three parts are intentionally retained to show the progression of the implementation rather than only the final state.

## Technologies and concepts

- Java
- Java Swing
- TCP sockets
- client-server architecture
- multithreading
- Java object serialisation
- event-driven input
- collision detection
- sprite animation
- audio playback
- object-oriented design

The project uses the Java standard library and does not depend on third-party libraries.

## Running the project

### Part 1

From `Part 1/RacingGame/src`:

```bash
javac *.java
java Main
```

### Part 2

From `Part 2/RacingGame/src`:

```bash
javac *.java
java Main
```

Local controls:

| Car | Turn left | Turn right | Increase speed | Decrease speed |
| --- | --- | --- | --- | --- |
| Red | `W` | `S` | `Q` | `A` |
| Yellow | `O` | `K` | `P` | `L` |

### Part 3

From `Part 3/RacingGame/src`, compile the source:

```bash
javac Client/*.java Server/*.java Shared/*.java
```

Then start the server:

```bash
java Server.TCPServer
```

Open two additional terminals and start one client in each:

```bash
java Client.TCPClient
```

Each client controls its own car with:

- `W` — turn left
- `S` — turn right
- `Q` — increase speed
- `A` — decrease speed

The multiplayer implementation currently expects the server to run on `localhost` using TCP port `4000`.

## Project context

This is an earlier Java project preserved as part of my public engineering portfolio.

Its main value is the progression from a small GUI and animation exercise into a stateful game and then into a networked client-server application. The final version combines user-interface programming, game-state management, concurrency, serialisation and socket communication within one project.
