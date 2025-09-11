# Geb AI Workshop
**Apache Community Over Code 2025**

Learn to create robust tests for existing applications using generative AI with Apache Groovy Geb.

## Workshop Overview

In this session, you'll learn to:
- 🔄 Transform rough tests into organized page objects
- 🧪 Demonstrate TDD on a new application built from scratch  
- ⚡ Use Selenium Grid and Geb's parallel test execution for faster, more efficient browser testing

## Prerequisites

- Java 17 or higher
- Basic knowledge of Groovy/Java
- Understanding of web applications
- Familiarity with testing concepts

## Getting Started

### 1. Setup the Environment

```bash
# Clone this repository
git clone https://github.com/jonnybot0/geb-ai-workshop.git
cd geb-ai-workshop

# Run the tests to verify setup
./gradlew test
```

### 2. Start Selenium Grid (Optional)

For parallel execution demonstrations:

```bash
# Start Selenium Grid with Docker
docker-compose up -d

# Verify Grid is running
open http://localhost:4444
```

## Workshop Exercises

### Exercise 1: Understanding Rough Tests

Examine `src/test/groovy/workshop/RoughTestsSpec.groovy` to see examples of unorganized tests:

- Tests are repetitive
- No reusable components  
- Hard to maintain
- Difficult to scale

**👨‍💻 Task**: Run the rough tests and note the issues:

```bash
./gradlew test --tests="*RoughTestsSpec"
```

### Exercise 2: Page Object Transformation

Study how the rough tests are transformed into organized page objects:

**Compare:**
- `RoughTestsSpec.groovy` (before)
- `OrganizedTestsSpec.groovy` (after)
- `pages/HomePage.groovy` (page object)
- `pages/ManualPage.groovy` (page object)

**👨‍💻 Task**: Run the organized tests:

```bash
./gradlew test --tests="*OrganizedTestsSpec"  
```

**Key Benefits of Page Objects:**
- ✅ Reusable components
- ✅ Centralized element definitions
- ✅ Better maintainability
- ✅ Clear separation of concerns

### Exercise 3: Test-Driven Development (TDD)

Practice TDD by creating tests first, then implementing functionality:

1. **Red**: Write a failing test
2. **Green**: Implement minimal code to pass
3. **Refactor**: Clean up and improve

**👨‍💻 Task**: Create a new page object for a different website:

```bash
# Create your own page object in src/test/groovy/workshop/pages/
# Example: GoogleHomePage.groovy
```

### Exercise 4: Parallel Test Execution

Learn how to speed up test execution with parallel processing:

**👨‍💻 Task**: Run tests in parallel:

```bash
# Regular execution
time ./gradlew test

# Parallel execution
time ./gradlew parallelTest

# Compare execution times!
```

Study `ParallelTestsSpec.groovy` to see parallel-safe test patterns.

### Exercise 5: Selenium Grid Integration

Scale your tests across multiple browsers and machines:

**👨‍💻 Task**: Run tests against Selenium Grid:

```bash
# Start Grid (if not already running)
docker-compose up -d

# Run tests against Grid
./gradlew gridTest -Dselenium.grid.url=http://localhost:4444/wd/hub

# Monitor at http://localhost:4444
```

## Configuration Files

### GebConfig.groovy
- **Chrome/Firefox drivers**: Automatic setup with WebDriverManager
- **Headless mode**: `geb.headless=true`
- **Grid integration**: `geb.env=grid`
- **Parallel support**: Built-in configuration

### build.gradle
- **Dependencies**: Latest Geb, Selenium, Spock versions
- **Parallel tasks**: Custom Gradle tasks for parallel execution
- **Grid tasks**: Selenium Grid integration

## Best Practices

### ✅ Do This
- Use page objects for maintainable tests
- Implement explicit waits
- Run tests in parallel when possible
- Use descriptive test names
- Keep tests independent

### ❌ Avoid This  
- Direct element interaction in tests
- Hard-coded waits (Thread.sleep)
- Interdependent tests
- Duplicate code across tests
- Testing implementation details

## Advanced Topics

### Custom Modules
Create reusable components for complex UI elements:

```groovy
class NavigationModule extends Module {
    static content = {
        links { $("nav a") }
        logo { $(".logo") }
    }
}
```

### Data-Driven Testing
Use Spock's `@Unroll` for parameterized tests:

```groovy
@Unroll("Verify #browser works correctly")
def "cross-browser testing"() {
    // Test logic here
    where:
    browser << ["chrome", "firefox", "safari"]
}
```

### Page Object Composition
Build complex pages from smaller components:

```groovy
class ComplexPage extends Page {
    static content = {
        header { module HeaderModule }
        sidebar { module SidebarModule }
        footer { module FooterModule }
    }
}
```

## Troubleshooting

### Common Issues

**Tests failing with WebDriver errors:**
```bash
# Update drivers
./gradlew test -Dwebdriver.chrome.driver=/path/to/chromedriver
```

**Parallel tests failing:**
```bash  
# Reduce parallelism
./gradlew test -Dtest.maxParallelForks=2
```

**Grid connection issues:**
```bash
# Check Grid status
curl http://localhost:4444/status
```

## Resources

- 📖 [Official Geb Documentation](https://gebish.org/manual/current/)
- 🐙 [Geb GitHub Repository](https://github.com/geb/geb)  
- 📝 [Spock Framework](http://spockframework.org/)
- 🌐 [Selenium Grid](https://selenium.dev/documentation/grid/)
- 🚀 [WebDriver Manager](https://github.com/bonigarcia/webdrivermanager)

## Workshop Completion

After completing all exercises, you should be able to:

- ✅ Transform rough tests into maintainable page objects
- ✅ Implement TDD practices with Geb
- ✅ Configure and use parallel test execution  
- ✅ Integrate with Selenium Grid for scalable testing
- ✅ Apply best practices for robust test automation

---

**Questions?** Open an issue or join the discussion!
