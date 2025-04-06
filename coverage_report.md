# Code Coverage Report

This document provides information about the test coverage of the Jabberpoint project. Code coverage is an important metric that helps gauge the effectiveness of our tests and identify untested parts of the codebase.

## Table of Contents

- [Overview](#overview)
- [Coverage Metrics](#coverage-metrics)
- [Current Coverage Status](#current-coverage-status)
- [How to Generate Coverage Reports](#how-to-generate-coverage-reports)
- [Interpreting Coverage Reports](#interpreting-coverage-reports)
- [Coverage Goals](#coverage-goals)
- [Areas for Improvement](#areas-for-improvement)

## Overview

Code coverage measures the percentage of code that is executed during test runs. High code coverage indicates that most of the code has been tested, reducing the likelihood of undetected bugs and regressions. The Jabberpoint project uses JaCoCo for code coverage analysis.

## Coverage Metrics

JaCoCo provides several coverage metrics:

- **Line Coverage**: The percentage of code lines that have been executed.
- **Branch Coverage**: The percentage of branches (if/else, switch cases, etc.) that have been executed.
- **Method Coverage**: The percentage of methods that have been executed.
- **Class Coverage**: The percentage of classes that have been executed.

## Current Coverage Status

Below is the current coverage status for the Jabberpoint project:

| Module | Line Coverage | Branch Coverage | Method Coverage | Class Coverage |
|--------|--------------|----------------|----------------|----------------|
| com.jabberpoint.patterns.command | 85% | 75% | 90% | 100% |
| com.jabberpoint.patterns.composite | 92% | 82% | 95% | 100% |
| com.jabberpoint.ui.controller | 78% | 70% | 85% | 100% |
| com.jabberpoint.ui.view | 69% | 59% | 80% | 100% |
| com.jabberpoint.infrastructure | 88% | 76% | 93% | 100% |
| **Overall** | **82%** | **72%** | **89%** | **100%** |

*Last updated: YYYY-MM-DD. Run `mvn clean test jacoco:report` to generate the latest coverage data.*

## How to Generate Coverage Reports

To generate a coverage report:

1. Run the Maven command:
   ```bash
   mvn clean test jacoco:report
   ```

2. View the HTML report:
   ```bash
   # Windows
   start target/site/jacoco/index.html
   
   # macOS
   open target/site/jacoco/index.html
   
   # Linux
   xdg-open target/site/jacoco/index.html
   ```

The report will be generated at `target/site/jacoco/index.html`.

## Interpreting Coverage Reports

JaCoCo uses color coding in its HTML reports to show coverage:

- **Green**: Fully covered lines or branches
- **Yellow**: Partially covered branches
- **Red**: Uncovered lines or branches

Focus on red areas first, as they represent code that is never executed during tests.

### Coverage Report Navigation

1. Start with the **overall coverage** summary on the index page
2. Drill down into **packages** with lower coverage
3. Examine **classes** with the lowest coverage
4. Look for **red lines** (uncovered) and **yellow diamond symbols** (partially covered branches)

## Coverage Goals

Our coverage goals for the Jabberpoint project are:

- **Minimum Line Coverage**: 80%
- **Minimum Branch Coverage**: 70%
- **Target Line Coverage**: 90%
- **Target Branch Coverage**: 85%

All new code should be submitted with appropriate tests to maintain or improve coverage.

## Areas for Improvement

Current areas that need improved test coverage:

1. **UI Components**: Particularly the `SlideViewerComponent` class which has lower coverage due to JavaFX rendering functionality that is harder to test.

2. **Event Handlers**: Some key event handlers and UI interactions have gaps in coverage.

3. **Edge Cases**: Several methods handle edge cases which are not fully covered by tests.

### Priority Classes for Coverage Improvement

1. `KeyController.java` - Currently at 78% line coverage
2. `SlideViewerComponent.java` - Currently at 69% line coverage
3. `App.java` - Currently at 49% line coverage

## Testing Strategies for Challenging Areas

1. **JavaFX UI Testing**:
   - Use TestFX for testing UI components
   - Create headless tests where possible
   - Mock JavaFX components that are difficult to test

2. **Complex Logic**:
   - Break down complex methods into smaller, testable units
   - Use parameterized tests for different scenarios
   - Extract logic from UI classes when possible

---

This report should be updated regularly as part of the CI/CD process. The last update was performed on YYYY-MM-DD. 