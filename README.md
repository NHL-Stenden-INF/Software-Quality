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
- [SOLID Principles in JabberPoint](#solid-principles-in-jabberpoint)

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

## SOLID Principles in JabberPoint

This project applies several SOLID principles. Below are examples from the codebase, including file locations and code snippets:

### Single Responsibility Principle (SRP)
Each class has a single responsibility. For example, `SlideItem` is only responsible for representing and drawing a slide item:

**File:** `src/main/java/com/jabberpoint/patterns/composite/SlideItem.java`
*Defines the abstract base for all slide items, encapsulating only the data and behavior for a slide item.*
```java
public abstract class SlideItem {
    protected int level;
    protected String text;
    protected Style style;
    // ...
    public abstract void draw(GraphicsContextWrapper gc, double x, double y);
}
```

**File:** `src/main/java/com/jabberpoint/patterns/composite/TitleItem.java`
*Represents a title item on a slide, only responsible for drawing the title.*
```java
public class TitleItem extends SlideItem {
    public TitleItem(String text, Style style) {
        super(text, style);
    }
    @Override
    public void draw(GraphicsContextWrapper gc, double width, double height) {
        // ...
    }
}
```

**File:** `src/main/java/com/jabberpoint/patterns/composite/BulletPointItem.java`
*Represents a bullet point item, only responsible for drawing a bullet point.*
```java
public class BulletPointItem extends SlideItem {
    public BulletPointItem(String text, Style style) {
        super(text, style);
    }
    @Override
    public void draw(GraphicsContextWrapper gc, double x, double y) {
        // ...
    }
}
```

### Open-Closed Principle (OCP)
Classes are open for extension but closed for modification. For example, new commands can be added by implementing the `Command` interface without modifying existing code:

**File:** `src/main/java/com/jabberpoint/patterns/command/Command.java`
*Defines the command interface, allowing new commands to be added without changing existing code.*
```java
public interface Command {
    void execute();
}
```

**File:** `src/main/java/com/jabberpoint/patterns/command/NextSlideCommand.java`
*Implements the Command interface to go to the next slide, demonstrating extension without modification.*
```java
public class NextSlideCommand implements Command {
    private final PresentationInterface presentation;
    public NextSlideCommand(PresentationInterface presentation) {
        this.presentation = presentation;
    }
    @Override
    public void execute() {
        presentation.nextSlide();
    }
}
```

**File:** `src/main/java/com/jabberpoint/patterns/command/ShowAboutCommand.java`
*Implements the Command interface to show the About dialog, another example of OCP.*
```java
public class ShowAboutCommand implements Command {
    @Override
    public void execute() {
        AboutBox.show();
    }
}
```

**File:** `src/main/java/com/jabberpoint/patterns/command/LoadDefaultCommand.java`
*Implements the Command interface to load the default presentation, showing how new commands can be added easily.*
```java
public class LoadDefaultCommand implements Command {
    private final PresentationInterface presentation;
    private final ViewInterface viewerFrame;
    private final XMLAccessor accessor;
    public LoadDefaultCommand(PresentationInterface presentation, ViewInterface viewerFrame, XMLAccessor accessor) {
        this.presentation = presentation;
        this.viewerFrame = viewerFrame;
        this.accessor = accessor;
    }
    @Override
    public void execute() {
        Command command = new OpenPresentationCommand(presentation, viewerFrame, accessor, "resources/default.xml");
        command.execute();
    }
}
```

### Liskov Substitution Principle (LSP)
Subtypes can be substituted for their base types. For example, all `SlideItem` subclasses can be used wherever a `SlideItem` is expected:

**File:** `src/main/java/com/jabberpoint/patterns/composite/Slide.java`
*Manages a list of SlideItem objects, allowing any subclass of SlideItem to be added and used interchangeably.*
```java
public void addItem(SlideItem item) {
    items.add(item);
}

public List<SlideItem> getItems() {
    return items;
}

// Usage of getItemsByType for LSP
public <T extends SlideItem> List<T> getItemsByType(Class<T> type) {
    return items.stream()
        .filter(type::isInstance)
        .map(type::cast)
        .collect(Collectors.toList());
}
```

**File:** `src/main/java/com/jabberpoint/patterns/composite/BodyTextItem.java`
*BodyTextItem is a SlideItem subclass, and can be used wherever SlideItem is expected.*
```java
public class BodyTextItem extends SlideItem {
    public BodyTextItem(String text, Style style) {
        super(text, style);
    }
    @Override
    public void draw(GraphicsContextWrapper gc, double width, double height) {
        // ...
    }
}
```

### Dependency Inversion Principle (DIP)
High-level modules depend on abstractions, not concrete implementations. For example, commands depend on the `PresentationInterface` abstraction:

**File:** `src/main/java/com/jabberpoint/patterns/command/NextSlideCommand.java`
*Depends on the PresentationInterface abstraction, not a concrete Presentation class.*
```java
public class NextSlideCommand implements Command {
    private final PresentationInterface presentation;
    public NextSlideCommand(PresentationInterface presentation) {
        this.presentation = presentation;
    }
    @Override
    public void execute() {
        presentation.nextSlide();
    }
}
```

**File:** `src/main/java/com/jabberpoint/patterns/composite/PresentationInterface.java`
*Defines the abstraction for presentations, allowing high-level modules to depend on this interface.*
```java
public interface PresentationInterface {
    void addSlide(Slide slide);
    List<Slide> getSlides();
    // ...
}
```

**File:** `src/main/java/com/jabberpoint/ui/view/SlideViewerFrame.java`
*Implements both SlideObserver and ViewInterface, and depends on the PresentationInterface abstraction.*
```java
public class SlideViewerFrame extends BorderPane implements SlideObserver, ViewInterface {
    private final SlideViewerComponent viewerComponent;
    private final PresentationInterface presentation;
    public SlideViewerFrame(PresentationInterface presentation) {
        this.presentation = presentation;
        this.viewerComponent = new SlideViewerComponent(presentation, new Style());
        // ...
        presentation.addObserver(this);
    }
    // ...
}
```

**File:** `src/main/java/com/jabberpoint/patterns/observer/SlideObserver.java`
*Defines the observer abstraction for presentations, supporting DIP.*
```java
public interface SlideObserver {
    void update(PresentationInterface presentation);
}
```

### More Examples
- **OCP/DIP:** All command classes (`GoToSlideCommand`, `PrevSlideCommand`, `SavePresentationCommand`, etc.) implement the `Command` interface and depend on abstractions like `PresentationInterface` and `ViewInterface`.
- **LSP:** All `SlideItem` subclasses (`TitleItem`, `SubtitleItem`, `BodyTextItem`, `BulletPointItem`, `BackgroundItem`, `BitmapItem`) can be used interchangeably in slides and are processed generically in `SlideViewerComponent`.
- **DIP/OCP:** The observer pattern is used with `SlideObserver` and `PresentationInterface`, allowing new observers to be added without modifying the presentation logic.

---

© 2025 NHLStenden Emmen - Software Quality Project
