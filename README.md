# Jabberpoint: A JavaFX Presentation Application

## Project Overview

Jabberpoint is a presentation application developed using JavaFX, designed to create and display slideshows with ease. It leverages the rich features of JavaFX to provide a smooth and interactive user experience.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation Guide](#installation-guide)
  - [Setting Up the Development Environment](#setting-up-the-development-environment)
  - [Cloning the Repository](#cloning-the-repository)
  - [Building the Project](#building-the-project)
  - [Running the Application](#running-the-application)
- [Running from GitHub Releases](#running-from-github-releases)
- [Usage](#usage)
- [Development Workflow](#development-workflow)
- [Code Conventions](#code-conventions)
- [License](#license)

## Features

- **Slide Creation:** Easily create slides with customizable content.
- **Interactive UI:** Intuitive interface for seamless navigation.
- **Cross-Platform:** Runs on any platform supporting JavaFX.
- **XML Support:** Load and save presentations in XML format.
- **Keyboard Navigation:** Navigate through slides using keyboard shortcuts.

## Prerequisites

Before setting up the project, ensure you have the following installed:

- **Java Development Kit (JDK):** Version 11 or higher. [Download JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- **JavaFX SDK:** [Download JavaFX SDK](https://gluonhq.com/products/javafx/)
- **Build Tool:** Maven for project management and building. [Install Maven](https://maven.apache.org/install.html)
- **Integrated Development Environment (IDE):** IntelliJ IDEA, Eclipse, or Visual Studio Code with Java support.

## Installation Guide

### Setting Up the Development Environment

1. **Install JDK:**
   - Download and install JDK 11 or higher.
   - Set the `JAVA_HOME` environment variable to point to your JDK installation.

2. **Install JavaFX SDK:**
   - Download the JavaFX SDK compatible with your operating system.
   - Extract the SDK to a preferred location on your system.

3. **Install Maven:**
   - Follow the [Maven installation guide](https://maven.apache.org/install.html) to set up Maven.

4. **Set Up Your IDE:**
   - **IntelliJ IDEA:**
     - Install the JavaFX plugin if not already included.
     - Configure the JavaFX SDK in your project settings.
   - **Eclipse:**
     - Install the e(fx)clipse plugin for JavaFX support.
     - Configure the JavaFX SDK in your project settings.
   - **Visual Studio Code:**
     - Install the Java Extension Pack.
     - Configure the JavaFX SDK in your workspace settings.

### Cloning the Repository

Clone the Jabberpoint repository to your local machine:

```bash
git clone https://github.com/yourusername/jabberpoint.git
cd jabberpoint
```

### Building the Project

To build the project, navigate to the project directory and run:

```bash
mvn clean install
```

This will compile the code, run tests, and create a JAR file in the `target` directory.

### Running the Application

#### During Development

You can run the application directly using Maven:

```bash
mvn javafx:run
```

Or from your IDE by executing the main class `com.jabberpoint.App`.

## Running from GitHub Releases

For users who want to run the application without building it:

1. Navigate to the [GitHub Releases page](https://github.com/yourusername/jabberpoint/releases)
2. Download the latest JAR file (`jabberpoint-x.y.z.jar`)
3. Make sure you have Java 11+ installed on your system
4. Download the JavaFX SDK for your platform from [Gluon](https://gluonhq.com/products/javafx/)
5. Extract the JavaFX SDK to a directory on your computer
6. Open a terminal/command prompt and run:

```bash
# Replace path-to-javafx with the actual path to your JavaFX SDK lib directory
# Replace x.y.z with the actual version number

# Windows
java --module-path "path-to-javafx\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar jabberpoint-x.y.z.jar

# macOS/Linux
java --module-path "path-to-javafx/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar jabberpoint-x.y.z.jar
```

Example with specific paths:
```bash
# Windows
java --module-path "C:\Program Files\JavaFX\javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar jabberpoint-1.2.3.jar

# macOS
java --module-path "/Users/username/JavaFX/javafx-sdk-17.0.2/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar jabberpoint-1.2.3.jar

# Linux
java --module-path "/home/username/javafx-sdk-17.0.2/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar jabberpoint-1.2.3.jar
```

## Usage

Once the application is running:

1. **Navigate slides:** Use arrow keys (← →) to move between slides
2. **Open a presentation:** Click File > Open or press Ctrl+O
3. **Save a presentation:** Click File > Save or press Ctrl+S
4. **Create a new slide:** Click Edit > New Slide
5. **Exit the application:** Click File > Exit or press Alt+F4

## Development Workflow

This project follows a structured Git workflow with four main branches:

1. **development**: Active development branch where new features are implemented
2. **acceptance**: Code that has passed verification and is ready for review
3. **test**: Code that has passed style checks and is ready for testing
4. **production**: Stable code that has passed all tests and is ready for release

The CI/CD pipeline automatically handles versioning:
- Development: Increments patch version with -SNAPSHOT suffix
- Acceptance: Converts to RC (Release Candidate) version
- Test: Finalizes to release version
- Production: Creates release, tags version, and prepares for next development cycle

## Code Conventions

Please see our [Code Conventions](CODECONVENTION.md) document for detailed information about coding standards and practices used in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

© 2023 NHLStenden Emmen - Software Quality Project
