import java.util.concurrent.Callable;
import java.util.function.Function;

public sealed interface Try<T> permits Success, Failure {

    T getResult();

    Throwable getError();

    static <T> Try<T> of(Callable<T> code) {
        try {
            return new Success<T>(code.call());
        } catch (Throwable throwable) {
            return new Failure<T>(throwable);
        }
    }

    default <R> Try<R> map(Function<T, R> mapper) {
        return this instanceof Success<T> ? Try.of(() -> mapper.apply(getResult())) : new Failure<R>(getError());
    }


}
