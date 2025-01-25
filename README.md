# Rust4J

A lightweight set of Java classes that brings Rust-inspired error handling patterns to Java.

## Features

- `Result<Ok,Err>`: A type-safe container for handling operations that can fail, similar to Rust's `Result` type
- Zero dependencies
- Java 8+ compatible

## Installation

Currently, you need to copy the `Result.java` class into your project. Proper artifact deployment coming soon!

## Usage

```java
// Create success results
Result<Integer, String> success = Result.ok(42);

// Create error results
Result<Integer, String> error = Result.err("Something went wrong");

// Pattern matching (should be use over unwrapping)
success.match(
    value -> System.out.println("Success: " + value),
    error -> System.out.println("Error: " + error)
);

// Unsafe unwrapping (for prototyping only)
try {
    Integer value = success.unwrap();
} catch (IllegalStateException e) {
    // Handle error case
}

// Custom error messages (will trigger an exeption with this message)
Integer value = success.expect("This operation should never fail");
```

### Why not Optional?

Unlike Java's `Optional` which only handles presence/absence of a value, `Result` provides:
- Typed error information and context -> You can use either an error classes or any other type of classes.
- Error propagation without exceptions
- Pattern matching similar to Rust/FP paradigms

Example:
```java
// Optional only shows absence
Optional<User> findUser(String id) {
    return Optional.empty(); // Reason for failure is lost
}

// Result preserves error context
Result<User, DbError> findUser(String id) {
    return Result.err(new DbError("Connection failed"));
}
```

## Contributing

This is just the beginning! Here are some areas where we'd love contributions:

- Additional Rust-inspired types (`Option`, `Either`)
- Method chaining support (`map`, `flatMap`, `andThen`)
- Integration with Java's Stream API
- Proper Maven/Gradle deployment
- Unit tests
- Documentation improvements
- Real-world usage examples

## License

MIT

## Maintainers

Currently looking for maintainers! If you're interested in helping develop this library, please open an issue or submit a PR.
