# rewards-game

This project is a Java-based gambling application that uses Maven for dependency management and packaging. It can be run either through Docker using `docker-compose` or directly by executing the `Application` class with the necessary arguments.

## Prerequisites

- Java 21 or higher
- Maven 3.8 or higher
- Docker and Docker Compose (if running via Docker)

## Building and Running the Application

### Option 1: Running with Docker Compose

You can build and run the project inside a Docker container using `docker-compose`. This will automatically package the application, create a Docker image, and start the service.

To run the application using Docker Compose, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/lprattisrio/rewards-game.git
   cd rewards-game
   
   
2. Run Docker Compose with the --build flag to build and start the container:
   ```bash
   docker-compose up --build

3. It is possible to change arguments on docker-compose.yml file

### Option 2: Running the Application Directly
You can also run the application directly by executing the Application class from the command line. This requires you to have the necessary environment set up with Java and Maven installed

1. First, ensure all dependencies are installed by running Maven:
   ```bash
   mvn clean install

2. Then, run the Application class with the required arguments:
    ```bash
   java -jar target/rewards-game-0.1.0-SNAPSHOT.jar --config src/main/resources/config.json --betting-amount 100

The --config argument points to the configuration file that the application will use, and --betting-amount specifies the initial betting amount.