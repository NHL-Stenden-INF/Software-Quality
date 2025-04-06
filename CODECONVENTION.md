# Code Conventions

This document outlines the coding standards and best practices for the Jabberpoint project. Adhering to these conventions ensures consistency, readability, and maintainability across the codebase.

## Table of Contents

- [Java Code Style](#java-code-style)
- [Naming Conventions](#naming-conventions)
- [Documentation](#documentation)
- [Project Structure](#project-structure)
- [Testing Standards](#testing-standards)
- [Git Workflow](#git-workflow)

## Java Code Style

### Formatting

- Use 4 spaces for indentation, not tabs
- Maximum line length: 100 characters
- Braces follow the "Egyptian style" (opening brace on the same line)
- One statement per line
- Files should end with a newline character

```java
// Correct
if (condition) {
    doSomething();
}

// Incorrect
if (condition) 
{
    doSomething();
}
```

### Whitespace

- No trailing whitespace
- One space after keywords like `if`, `for`, `while`
- No space between method names and parentheses
- One space around operators (`+`, `-`, `*`, `/`, `=`, etc.)

```java
// Correct
int result = a + b * c;

// Incorrect
int result=a+b*c;
```

## Naming Conventions

- **Classes**: PascalCase, noun phrases (e.g., `SlideViewerFrame`)
- **Interfaces**: PascalCase, adjective phrases or nouns (e.g., `PresentationInterface`)
- **Methods**: camelCase, verb phrases (e.g., `loadPresentation`)
- **Variables**: camelCase, meaningful names (e.g., `slideIndex`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_SLIDES`)
- **Packages**: all lowercase, use reverse domain name (e.g., `com.jabberpoint.ui.view`)

Avoid:
- Single-letter variable names (except for loop indices)
- Abbreviations unless widely accepted
- Hungarian notation
- Ambiguous names

## Documentation

- All public classes, interfaces, and methods must have JavaDoc comments
- Comments should explain "why", not "what" (the code should be self-explanatory)
- Keep comments up-to-date when modifying code

### JavaDoc Format

```java
/**
 * Brief description of the class/method.
 * 
 * More detailed explanation if necessary.
 *
 * @param paramName Description of the parameter
 * @return Description of the return value
 * @throws ExceptionType Description of when this exception is thrown
 */
```

## Project Structure

The project follows a standard Maven structure with additional organization by feature and pattern:

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── jabberpoint/
│   │           ├── infrastructure/    # I/O and system-level classes
│   │           ├── patterns/          # Design pattern implementations
│   │           │   ├── command/       # Command pattern classes
│   │           │   ├── composite/     # Composite pattern classes
│   │           │   └── factory/       # Factory pattern classes
│   │           ├── style/             # Style-related classes 
│   │           ├── ui/                # User interface components
│   │           │   ├── controller/    # UI controllers
│   │           │   └── view/          # View components
│   │           └── App.java           # Main application entry point
│   └── resources/                     # Non-code assets
└── test/
    └── java/
        └── com/
            └── jabberpoint/           # Test classes mirror main structure
```

## Testing Standards

- All code should be covered by unit tests
- Target minimum 80% code coverage
- Tests should be independent and isolated
- Follow the AAA pattern (Arrange, Act, Assert)
- Test class names should match the class they test with a `Test` suffix
- One test method per feature/behavior being tested

```java
@Test
public void testSlideNavigation_whenNextSlide_shouldIncrementCurrentSlideNumber() {
    // Arrange
    Presentation presentation = new Presentation();
    presentation.addSlide(new Slide("Slide 1"));
    presentation.addSlide(new Slide("Slide 2"));
    
    // Act
    presentation.nextSlide();
    
    // Assert
    assertEquals(1, presentation.getCurrentSlideNumber());
}
```

## Git Workflow

- The `development` branch is the main development branch
- Create feature branches from `development` for new features
- Branch naming: `feature/descriptive-feature-name` or `fix/issue-description`
- Commit messages should be clear and descriptive, starting with a verb
- Pull requests require passing CI checks and code review before merging

### Commit Message Format

```
[COMPONENT] Short description of the change

More detailed explanation if necessary.

Refs #123
```

Example:
```
[UI] Fix navigation button not responding to click events

The event listener was not properly registered due to a timing issue.
This commit ensures the listener is added after the component is initialized.

Refs #456
```

---

These conventions may evolve over time. Please refer to the latest version of this document for current standards. 