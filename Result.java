import java.util.function.Consumer;

/**
 * A container type representing either a successful value (Ok) or an error value (Err).
 * Equivalent to Rust's Result type for error handling without exceptions.
 *<p>
 * Unlike Java's Optional which only handles presence/absence of a value,
 * Result allows explicit error handling with two distinct types:
 * <ul>
 *     <li>Can contain typed error information and context</li>
 *     <li>Enables propagation of errors without exceptions</li>
 *     <li>Supports pattern matching similar to Rust/Functional Programming</li>
 * </ul>
 *<p>
 * Example usage:
 * <pre>
 * {@code
 * // Optional only shows absence
 * private Optional<User> findUser(String id) {
 *     return Optional.empty(); // Reason for failure is lost
 * }
 *
 * // Result preserves error context
 * private Result<User, DbError> findUser(String id) {
 *     return Result.err(new DbError("Connection failed"));
 * }
 * }
 * </pre>
 *
 * @param <Ok>  The type of the success value
 * @param <Err> The type of the error value
 */
public class Result<Ok, Err> {
    private final Ok ok;
    private final Err err;
    private final boolean isOk;

    /**
     * Private constructor to ensure instances are created via factory methods.
     */
    private Result(Ok ok, Err err, boolean isOk) {
        this.ok = ok;
        this.err = err;
        this.isOk = isOk;
    }

    /**
     * Creates a new Result containing a success value.
     *
     * @param ok   The success value
     * @param <Ok> The type of the success value
     * @param <Err> The type of the error value
     * @return A new Result containing the success value
     */
    public static <Ok, Err> Result<Ok, Err> ok(Ok ok) {
        return new Result<>(ok, null, true);
    }

    /**
     * Creates a new Result containing an error value.
     *
     * @param err  The error value
     * @param <Ok> The type of the success value
     * @param <Err> The type of the error value
     * @return A new Result containing the error value
     */
    public static <Ok, Err> Result<Ok, Err> err(Err err) {
        return new Result<>(null, err, false);
    }

    /**
     * Checks if this Result contains a success value.
     *
     * @return true if this Result contains a success value, false otherwise
     */
    public boolean isOk() {
        return isOk;
    }

    /**
     * Checks if this Result contains an error value.
     *
     * @return true if this Result contains an error value, false otherwise
     */
    public boolean isErr() {
        return !isOk;
    }

    /**
     * Extracts the success value from this Result.
     *
     * @return The success value
     * @throws IllegalStateException if this Result contains an error value
     */
    public Ok unwrap() {
        if (!isOk) {
            throw new IllegalStateException("Result is err");
        }
        return ok;
    }

    /**
     * Extracts the success value from this Result, or returns a default value if this Result contains an error value.
     *
     * @param defaultValue The value to return if this Result contains an error value
     * @return The success value if this Result contains a success value, otherwise the default value
     */
    public Ok unwrapOr(Ok defaultValue) {
        return isOk ? ok : defaultValue;
    }

    /**
     * Extracts the error value from this Result.
     *
     * @return The error value
     * @throws IllegalStateException if this Result contains a success value
     */
    public Err unwrapErr() {
        if (isOk) {
            throw new IllegalStateException("Result is ok");
        }
        return err;
    }

    /**
     * Extracts the success value from this Result with a custom error message.
     *
     * @param message The error message to use if this Result contains an error value
     * @return The success value
     * @throws IllegalStateException if this Result contains an error value
     */
    public Ok expect(String message) {
        if (!isOk) {
            throw new IllegalStateException(message);
        }
        return ok;
    }

    /**
     * Pattern matches on this Result, executing the appropriate consumer based on the contained value.
     *
     * @param okConsumer  The consumer to execute if this Result contains a success value
     * @param errConsumer The consumer to execute if this Result contains an error value
     */
    public void match(Consumer<Ok> okConsumer, Consumer<Err> errConsumer) {
        if (isOk) {
            okConsumer.accept(ok);
        } else {
            errConsumer.accept(err);
        }
    }
}
