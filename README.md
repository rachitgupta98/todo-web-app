# Todo FullStack Web App

## Table of Contents

- [Project Overview](#project-overview)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Frontend Setup (React)](#frontend-setup-react)
  - [Backend Setup (Java)](#backend-setup-java)
- [Docker MySql Setup](#docker-setup)
- [Application Snapshot](#application-snapshot)
- [License](#license)

## Project Overview

It is a full stack todo application which has the following features:

1. ##### Task Management:
  - Users can list, create, modify, and delete todo items through an intuitive web interface.
  - Each todo item will have fields for title, description, due date, priority, and status (complete/incomplete).
2. ##### Cancellation Option:
  - Todo items can be marked as canceled, providing users with the flexibility to manage unexpected changes or cancellations.
3. ##### Reminders:
  - Users can set reminders for individual todo items, receiving notifications or alerts based on their preferences.
  - Reminders can be customized to suit the user's schedule and preferences.
4. ##### Recurring Tasks:
  - Todo items can be configured to be recurring in nature, allowing users to set daily, weekly, or custom recurring patterns.
  - Recurring tasks will automatically generate new instances based on the specified recurrence rules.

## Prerequisites

Before you begin, ensure you have the following installed:

- **Node.js and npm**: [Install Node.js](https://nodejs.org/)
- **Java JDK 11**: [Install Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- **Maven**: [Install Maven](https://maven.apache.org/install.html)
- **Docker**: [Install Docker](https://www.docker.com/get-started)
- **IntelliJ IDEA**: [Download IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- **Visual Studio Code**: [Download VS Code](https://code.visualstudio.com/)

## Getting Started

### Frontend Setup (React)

Follow these steps to set up and run the React frontend application:

1. **Navigate to the Frontend Directory**:

    ```bash
    cd todo-frontend/vite-project/
    ```

2. **Install Dependencies**:

    Run the following command to install all necessary packages:

    ```bash
    npm install
    ```

3. **Start the Development Server**:

    Start the React application in development mode:

    ```bash
    npm run dev
    ```

    The application should be accessible at `http://localhost:5173`.

### Backend Setup (Java)

Follow these steps to set up and run the Java backend application:

1. **Navigate to the Backend Directory**:

    ```bash
    cd yoda
    ```

2. **Clean and Build the Project**:

    Run the following command to clean and build the project:

    ```bash
    mvn clean install
    ```

3. **Run the Application**:

    Start the Java backend application:

    ```bash
    By clicking the run or debug button from the intellij
    ```

    The backend should be accessible at `http://localhost:8989`.

## Docker Setup

I used docker to create a MySql container that will spin up by using following steps

1. **Build Docker Images**:

    Navigate to the project root and build the MySQL Docker images:

    ```bash
    docker-compose up -d
    ```

3. **Access the Application**:

    - **Frontend**: `http://localhost:5173`
    - **Backend**: `http://localhost:8989`



## Application Snapshot
<img width="906" alt="image" src="https://github.com/rachitgupta98/todo-web-app/assets/43313965/789a7b25-fcc8-4f0e-b110-8588fca31227">


## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
