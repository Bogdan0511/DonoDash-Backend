# DonoDash-Backend

## Overview

Welcome to the backend repository of DonoDash, the donations dashboard for content creators! This server-side application is implemented using Java 17 and Spring Boot 3.2.3, integrating Spring Security 6 for enhanced security. The project utilizes Maven for dependency management and Docker Compose to integrate seamlessly with a PostgreSQL database.

## Features

- **Java 17:** The application is developed using Java 17, taking advantage of the latest language features and improvements.

- **Spring Boot 3.2.3:** Leveraging the power of the Spring Boot framework to simplify the development process and create a robust, scalable backend.

- **Spring Security 6:** Ensuring the security of the application with Spring Security 6, providing authentication and authorization features.

- **Maven:** The project utilizes Maven for dependency management, making it easy to manage and build the project.

- **Docker Compose:** The application is designed to work seamlessly with Docker Compose, allowing for easy deployment and integration with a PostgreSQL database.

- **Integration with YouTube Data API:** DonoDash integrates with the YouTube Data API, providing content creators with real-time data on their donations and contributions.

## Getting Started

Follow these steps to get the DonoDash backend up and running on your local machine.

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Bogdan0511/DonoDash-Backend.git
   ```

2. **Navigate to the Project Directory:**
   ```bash
   cd DonoDash-Backend
   ```

3. **Build the Project with Maven:**
   ```bash
   mvn clean install
   ```

4. **Run the Application using Spring Boot:**
   ```bash
   java -jar target/donodash-backend-1.0.0.jar
   ```

## Docker Compose Setup

To run the application with Docker Compose, follow these additional steps:

1. **Install Docker**
   If you haven't installed Docker, follow the instructions [here](https://www.docker.com/products/docker-desktop/).

2. **Build and Start Docker Containers:**
   ```bash
   docker-compose up
   ```

3. **Access the Application:**
   Open your web browser and go to [http://localhost:8080](http://localhost:8080)

Happy coding! 🚀
